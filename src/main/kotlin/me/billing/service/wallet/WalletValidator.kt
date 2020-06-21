package me.billing.service.wallet

import me.billing.exception.WalletException
import me.billing.model.Wallet
import me.billing.service.ValidatorService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class WalletValidator : ValidatorService<Wallet> {
    override fun validate(wallet: Wallet?, id: Long): Wallet {
        if (wallet == null) {
            throw WalletException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Wallet with id=%d doesn't exit", id)
            )
        }
        return wallet
    }
}