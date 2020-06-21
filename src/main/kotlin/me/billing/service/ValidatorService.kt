package me.billing.service

import me.billing.model.Wallet

interface ValidatorService<T> {
    fun validate(obj: T?, id: Long): Wallet
}