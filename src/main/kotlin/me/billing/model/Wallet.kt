package me.billing.model

import java.math.BigDecimal

data class Wallet(val id: Long, val name: String, val balance: BigDecimal)