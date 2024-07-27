package com.cmc.purithm.data.remote

internal object ApiConfig {
    const val BASE_URL = "https://purithm.shop/"
    var ACCESS_TOKEN = ""
}

internal object ApiCode {
    const val SUCCESS = "P100"
    const val NEED_TERM_OF_SERVICE = "P101"
    const val AUTHORIZATION_FAIL = "P401"
}