package me.billing.exception

import org.springframework.http.HttpStatus

class WalletException(code: HttpStatus, message: String) : HttpException(code, message)