package me.billing.service

import me.billing.api.dto.WalletBalanceDto
import me.billing.model.Wallet

interface BillingService {
    fun create(wallet: Wallet)

    fun changeBalance(walletBalanceDto: WalletBalanceDto)

    fun getWalletWithLock(id: Long): Wallet
}