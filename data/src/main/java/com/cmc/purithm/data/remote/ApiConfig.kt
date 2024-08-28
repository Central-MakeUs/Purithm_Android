package com.cmc.purithm.data.remote

internal object ApiConfig {
    const val BASE_URL = "https://purithm.shop/"
    const val PAGE_SIZE = 16

    var ACCESS_TOKEN = ""
}

internal object ApiCode {
    const val NEED_TERM_OF_SERVICE = 40300
    const val TOKEN_VALIDATE_FAIL = 40100
    const val TOKEN_EXPIRE = 40101
    const val REJOIN_RESTRICTED =  40301
}