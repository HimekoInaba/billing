package me.billing.service.transfer

import me.billing.api.dto.TransferDto
import me.billing.dao.OperationRepository
import me.billing.dao.WalletRepository
import me.billing.exception.OperationException
import me.billing.exception.WalletException
import me.billing.model.Operation
import me.billing.model.OperationType
import me.billing.model.Wallet
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransferService(
        val walletRepository: WalletRepository,
        val operationRepository: OperationRepository
) {

    @Transactional
    fun transfer(transferDto: TransferDto) {
        val existing: Operation? = operationRepository.findByIdempotenceKey(transferDto.idempotenceKey)

        if (existing != null) {
            // operation already exists, return 200
            return
        }

        // TODO FIX DEADLOCK WITH ID SORTING
        val senderWallet: Wallet? = walletRepository.findByIdWithLock(transferDto.fromId)
        if (senderWallet == null) {
            throwWalletDoesntExistException(transferDto.fromId)
        } else if (senderWallet.balance.subtract(transferDto.amount)
                        .compareTo(BigDecimal.ZERO) == -1) {
            throw OperationException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Sender wallet=%d doesn't have enough balance to make a transfer", transferDto.amount)
            )
        }

        val receiverWallet: Wallet? = walletRepository.findByIdWithLock(transferDto.toId)
        if (receiverWallet == null) {
            throwWalletDoesntExistException(transferDto.toId)
        }

        if (senderWallet?.id != null) {
            walletRepository.changeBalance(senderWallet.id, senderWallet.balance.subtract(transferDto.amount))
        }

        if (receiverWallet?.id != null) {
            walletRepository.changeBalance(receiverWallet.id, receiverWallet.balance.add(transferDto.amount))
        }

        operationRepository.save(
                Operation(
                        null,
                        transferDto.idempotenceKey,
                        transferDto.fromId,
                        transferDto.toId,
                        transferDto.amount,
                        LocalDateTime.now(),
                        OperationType.CREDIT
                )
        )
    }

    private fun throwWalletDoesntExistException(walletId: Long) {
        throw WalletException(
                HttpStatus.BAD_REQUEST,
                String.format("Wallet with id=%d doesn't exit", walletId)
        )
    }
}
