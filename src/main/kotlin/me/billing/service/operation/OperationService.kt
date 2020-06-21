package me.billing.service.operation

import me.billing.api.dto.TransferDto
import me.billing.dao.OperationRepository
import me.billing.model.Operation
import me.billing.model.OperationType
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OperationService(val operationRepository: OperationRepository) {

    public fun createTransferOperations(dto: TransferDto) {
        val now: LocalDateTime = LocalDateTime.now()
        operationRepository.save(
                Operation(
                        null,
                        dto.idempotenceKey,
                        dto.fromId,
                        dto.toId,
                        dto.amount,
                        now,
                        OperationType.CREDIT
                )
        )

        operationRepository.save(
                Operation(
                        null,
                        dto.idempotenceKey,
                        dto.toId,
                        dto.fromId,
                        dto.amount,
                        now,
                        OperationType.DEPOSIT
                )
        )
    }
}