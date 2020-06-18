package me.billing.service

import me.billing.api.dto.TransferDto
import me.billing.dao.WalletRepository
import org.springframework.stereotype.Service

@Service
class TransferService(walletRepository: WalletRepository) {

    fun transfer(transferDto: TransferDto) {
    }

}
