package me.billing.api.dto

import java.math.BigDecimal

data class WalletBalanceDto(val walletId: Long, val balance: BigDecimal)