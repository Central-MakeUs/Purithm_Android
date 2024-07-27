package com.cmc.purithm.data.remote.interceptor

import com.cmc.purithm.data.local.datasource.AuthDataStore
import com.cmc.purithm.data.remote.ApiConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AddTokenInterceptor @Inject constructor(
    private val authDataStore: AuthDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        if(ApiConfig.ACCESS_TOKEN.isEmpty()){
            runBlocking {
                ApiConfig.ACCESS_TOKEN = authDataStore.getAccessToken()
            }
        }
        builder.addHeader("Authorization", "Bearer ${ApiConfig.ACCESS_TOKEN}")
        return chain.proceed(builder.build())
    }
}