package com.cmc.purithm.domain.exception

sealed class AuthException : BaseException() {
    class InvalidTokenException(override val message : String) : AuthException()
}