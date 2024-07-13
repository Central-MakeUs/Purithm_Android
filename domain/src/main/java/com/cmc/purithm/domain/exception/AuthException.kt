package com.cmc.purithm.domain.exception

sealed class AuthException : Exception() {
    class ExpireTokenException(val code : Int = 3) : AuthException()
}