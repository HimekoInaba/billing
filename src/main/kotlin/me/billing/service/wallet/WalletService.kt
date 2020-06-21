package me.billing.service.wallet

import me.billing.api.dto.WalletBalanceDto
import me.billing.dao.OperationRepository
import me.billing.dao.WalletRepository
import me.billing.exception.WalletException
import me.billing.model.Operation
import me.billing.model.Wallet
import me.billing.service.BillingService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(
        val walletRepository: WalletRepository,
        val operationRepository: OperationRepository,
        val walletValidator: WalletValidator
): BillingService {
    @Transactional
    override fun create(wallet: Wallet) {
        val existing: Wallet? = walletRepository.findByName(wallet.name)

        if (existing != null) {
            throw WalletException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Wallet with name=%s already exists!", wallet.name)
            )
        }

        walletRepository.save(wallet)
    }

    fun getWalletWithLock(id: Long): Wallet {
        val wallet: Wallet? = walletRepository.findOneByIdWithLock(id)
        return walletValidator.validate(wallet, id)
    }

    override fun changeBalance(dto: WalletBalanceDto) {
        val existing: Operation? = operationRepository.findByIdempotenceKey(dto.idempotenceKey)
        if (existing != null) {
            // operation already exists
            return
        }


    }
}