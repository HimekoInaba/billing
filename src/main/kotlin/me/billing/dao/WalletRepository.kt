package me.billing.dao

import me.billing.api.dto.TransferDto
import me.billing.model.Wallet
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType

@Repository
interface WalletRepository: CrudRepository<Wallet, Long> {
    fun findByName(name: String): Wallet?

    fun save(wallet: Wallet): Wallet

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query
    fun tranfer(transferDto: TransferDto)
}