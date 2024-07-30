package com.cmc.purithm.data.remote

internal object ApiConfig {
    const val BASE_URL = "https://purithm.shop/"
    var ACCESS_TOKEN = ""
}

internal object ApiCode {
    const val NEED_TERM_OF_SERVICE = 20001
    const val TOKEN_VALIDATE_FAIL = 40100
    const val TOKEN_EXPIRE = 40101
}