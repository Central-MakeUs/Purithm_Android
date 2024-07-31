package com.cmc.purithm.data.remote.interceptor

import android.util.Log
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
                val accessToken = authDataStore.getAccessToken()
                Log.d(TAG, "intercept: accessToken is empty in ApiConfig, setting value = $accessToken")
                ApiConfig.ACCESS_TOKEN = accessToken
            }
        }
        builder.addHeader("Authorization", "Bearer ${ApiConfig.ACCESS_TOKEN}")
        Log.d(TAG, "intercept: Header = Bearer ${ApiConfig.ACCESS_TOKEN}")
        return chain.proceed(builder.build())
    }

    companion object {
        private const val TAG = "AddTokenInterceptor"
    }
}