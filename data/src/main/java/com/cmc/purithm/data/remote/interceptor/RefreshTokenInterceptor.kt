package com.cmc.purithm.data.remote.interceptor

import com.cmc.purithm.data.local.datasource.AuthDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * TODO : 현재 사용안함, API 구현 후 사용
 * */
internal class RefreshTokenInterceptor @Inject constructor(
    private val authDataStore: AuthDataStore
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if(response.code == 401){
            runBlocking {
                kotlin.runCatching {

                }.onSuccess {

                }.onFailure {

                }
            }
            response.close()
            return chain.proceed(request)
        }
        return response
    }
}
