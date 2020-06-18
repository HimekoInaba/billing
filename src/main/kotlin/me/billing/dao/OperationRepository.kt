package me.billing.dao

import me.billing.model.Operation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OperationRepository: CrudRepository<Operation, Long> {
    fun findByIdempotenceKey(idempotenceKey: String): Operation?
}