package me.billing.service.transfer

import me.billing.api.dto.TransferDto
import me.billing.dao.OperationRepository
import me.billing.dao.WalletRepository
import me.billing.exception.OperationException
import me.billing.model.Operation
import me.billing.model.Wallet
import me.billing.service.BillingService
import me.billing.service.operation.OperationService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransferService(
        val operationRepository: OperationRepository,
        val operationService: OperationService,
        val walletRepository: WalletRepository,
        val walletService: BillingService
) {

    @Transactional
    fun transfer(dto: TransferDto) {
        val existing: Operation? = operationRepository.findByIdempotenceKey(dto.idempotenceKey)
        if (existing != null) {
            // operation already exists, return 200
            return
        }

        val senderWallet: Wallet
        val receiverWallet: Wallet
        if (dto.fromId > dto.toId) {
            senderWallet = walletService.getWalletWithLock(dto.fromId)
            receiverWallet = walletService.getWalletWithLock(dto.toId)
        } else {
            receiverWallet = walletService.getWalletWithLock(dto.toId)
            senderWallet = walletService.getWalletWithLock(dto.fromId)
        }

        if (senderWallet!!.hasEnoughBalanceForOperation(dto.amount)) {
            throw OperationException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Sender wallet=%s doesn't have enough balance to make a transfer", senderWallet.name)
            )
        }

        walletRepository.changeBalance(senderWallet!!.id!!, senderWallet!!.balance.subtract(dto.amount))
        walletRepository.changeBalance(receiverWallet!!.id!!, receiverWallet!!.balance.add(dto.amount))

        operationService.createTransferOperations(dto)
    }
}
