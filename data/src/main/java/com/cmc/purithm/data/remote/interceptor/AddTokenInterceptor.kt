package com.cmc.purithm.data.remote.interceptor

import com.cmc.purithm.data.local.datasource.AuthDataStore
import com.cmc.purithm.data.remote.ApiSetting
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AddTokenInterceptor @Inject constructor(
    private val authDataStore: AuthDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()

        if(ApiSetting.ACCESS_TOKEN.isEmpty()){
            runBlocking {
                ApiSetting.ACCESS_TOKEN = authDataStore.getAccessToken()
            }
        }
        builder.addHeader("Authorization", "Bearer ${ApiSetting.ACCESS_TOKEN}")
        return chain.proceed(builder.build())
    }
}