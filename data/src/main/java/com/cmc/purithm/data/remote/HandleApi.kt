package com.cmc.purithm.data.remote

import com.cmc.purithm.data.remote.dto.BaseResponse
import com.cmc.purithm.domain.exception.AuthException
import com.google.gson.Gson
import retrofit2.HttpException

internal object HandleApi {
    private val gson = Gson()
    inline fun <T> callApi(
        mapper: () -> T
    ) = try {
        mapper.invoke()
    } catch (e: HttpException) {
        e.printStackTrace()
        throw createException(e.code())
    } catch (e: Exception) {
        e.printStackTrace()
        throw Exception("알 수 없는 에러")
    }

    fun createException(code: Int) = when (code) {
        401 -> AuthException.InvalidTokenException("토큰이 유효하지 않습니다")
        else -> Exception("알 수 없는 에러")
    }
}