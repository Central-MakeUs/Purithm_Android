package com.cmc.purithm.domain.exception

sealed class AuthException : BaseException() {
    class ExpireTokenException(override val message : String) : AuthException()
    class InvalidTokenException(override val message : String) : AuthException()
}