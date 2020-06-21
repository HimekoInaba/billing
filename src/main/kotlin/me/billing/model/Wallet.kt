package me.billing.model

import me.billing.exception.WalletException
import org.springframework.http.HttpStatus
import java.math.BigDecimal

data class Wallet(val id: Long?, val name: String, val balance: BigDecimal) {
    public fun hasEnoughBalanceForOperation(charge: BigDecimal?): Boolean {
        if (charge == null) {
            throw WalletException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Wallet=%s doesn't have enough balance for operation", this.name)
            )
        }

        return this.balance
                .subtract(charge)
                .compareTo(BigDecimal.ZERO) == -1
    }
}