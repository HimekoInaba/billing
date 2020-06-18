package me.billing.api

import me.billing.api.dto.TransferDto
import me.billing.model.Wallet
import me.billing.service.TransferService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api")
class WalletController(val walletDao: WalletDao, val transferService: TransferService) {

    @PostMapping("/")
    fun create(wallet: Wallet) {
        walletDao.create(wallet)
    }

    @PostMapping("/transfer")
    fun transfer(transferDto: TransferDto) {
        transferService.transfer(transferDto)
    }
}