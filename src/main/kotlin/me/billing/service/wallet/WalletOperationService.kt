package me.billing.service.wallet

import me.billing.api.dto.WalletBalanceDto
import me.billing.dao.WalletRepository
import me.billing.exception.WalletException
import me.billing.model.Wallet
import me.billing.service.OperationService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletOperationService(val walletRepository: WalletRepository): OperationService {
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

    override fun changeBalance(walletBalanceDto: WalletBalanceDto) {

    }
}