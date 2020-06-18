package me.billing.api.dto

import java.math.BigDecimal

data class TransferDto(
        val fromId: Long,
        val toId: Long,
        val amount: BigDecimal,
        val idempotenceKey: String
)