package me.billing.dao

import me.billing.model.Wallet
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import javax.persistence.LockModeType

@Repository
interface WalletRepository : CrudRepository<Wallet, Long> {
    fun findByName(name: String): Wallet?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findOneByIdWithLock(id: Long): Wallet

    fun save(wallet: Wallet): Wallet

    @Query("UPDATE Wallet SET balance=:amount WHERE id=:id")
    fun changeBalance(id: Long, amount: BigDecimal)
}