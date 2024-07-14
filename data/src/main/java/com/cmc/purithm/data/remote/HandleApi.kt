package com.cmc.purithm.data.remote

import com.cmc.purithm.data.remote.dto.BaseResponse
import com.google.gson.Gson
import retrofit2.HttpException

internal object HandleApi {
    private val gson = Gson()
    inline fun <T> callApi(
        mapper: () -> T
    ) = try {
        // TODO : Response 형식에 따라서 준비
        mapper.invoke()
    } catch (e: HttpException) {
        val errorBody = e.response()?.errorBody().toString()
        val errorResponse = gson.fromJson(errorBody, BaseResponse::class.java)
        throw createException(errorResponse.code)
    } catch (e: Exception) {
        e.printStackTrace()
        throw Exception("알 수 없는 에러")
    }

    // TODO : 서버에서 정의한 코드에 따라 예외처리
    fun createException(code: String) = when (code) {
        else -> Exception("알 수 없는 에러")
    }
}