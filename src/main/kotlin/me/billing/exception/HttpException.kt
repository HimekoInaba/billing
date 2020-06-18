package me.billing.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class HttpException: RuntimeException {
    private val code: HttpStatus
    final override val message: String

    constructor(code: HttpStatus, message: String) {
        this.code = code
        this.message = message
    }
}