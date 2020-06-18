package me.billing.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Operation(
        val id: Long?,
        val idempotenceKey: String,
        val fromId: Long,
        val toId: Long,
        val amount: BigDecimal,
        val operationDate: LocalDateTime,
        val type: OperationType
)