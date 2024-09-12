package com.cmc.purithm.data.remote

import com.cmc.purithm.data.BuildConfig

internal object ApiConfig {
    var BASE_URL = if(BuildConfig.DEBUG) {
        "https://purithm.shop:8081/"
    } else {
        "https://purithm.shop/"
    }
    const val PAGE_SIZE = 16

    var ACCESS_TOKEN = ""
}

internal object ApiCode {
    const val NEED_TERM_OF_SERVICE = 40300
    const val TOKEN_VALIDATE_FAIL = 40100
    const val TOKEN_EXPIRE = 40101
    const val REJOIN_RESTRICTED =  40301
}