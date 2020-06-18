package me.billing.api.dto

import java.math.BigDecimal

data class TransferDto(val from: String, val to: String, val amount: BigDecimal)