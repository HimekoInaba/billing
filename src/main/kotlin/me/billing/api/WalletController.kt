package me.billing.api

import me.billing.api.dto.TransferDto
import me.billing.api.dto.WalletBalanceDto
import me.billing.model.Wallet
import me.billing.service.OperationService
import me.billing.service.transfer.TransferService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/api/wallet")
class WalletController(val walletService: OperationService, val transferService: TransferService) {

    @PostMapping("/")
    fun create(wallet: Wallet) {
        walletService.create(wallet)
    }

    @PostMapping("/change")
    fun change(walletBalanceDto: WalletBalanceDto) {
        walletService.changeBalance(walletBalanceDto)
    }

    @PostMapping("/transfer")
    fun transfer(transferDto: TransferDto) {
        transferService.transfer(transferDto)
    }
}
