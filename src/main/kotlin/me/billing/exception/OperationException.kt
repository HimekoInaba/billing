package me.billing.exception

import org.springframework.http.HttpStatus

class OperationException(code: HttpStatus, message: String): HttpException(code, message)