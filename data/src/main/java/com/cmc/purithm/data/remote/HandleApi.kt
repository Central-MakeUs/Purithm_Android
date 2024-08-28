package com.cmc.purithm.data.remote

import android.util.Log
import com.cmc.purithm.data.remote.dto.base.ErrorResponse
import com.cmc.purithm.domain.exception.AuthException
import com.cmc.purithm.domain.exception.MemberException
import com.google.gson.Gson
import retrofit2.HttpException

internal object HandleApi {
    private const val TAG = "HandleApi"
    private val gson = Gson()
    inline fun <T> callApi(
        mapper: () -> T
    ): T {
        return try {
            mapper.invoke()
        } catch (e: HttpException) {
            e.printStackTrace()
            throw createException(e.response()?.errorBody()?.string())
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("알 수 없는 에러가 발생했습니다.")
        }
    }

    // 앱에서 사용되는 Exception
    private fun createException(errorBody: String?): Exception {
        Log.e(TAG, "createException: start")
        val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
        return when (errorResponse.code) {
            ApiCode.TOKEN_EXPIRE -> AuthException.ExpireTokenException(errorResponse.message)
            ApiCode.TOKEN_VALIDATE_FAIL -> AuthException.InvalidTokenException(errorResponse.message)
            ApiCode.NEED_TERM_OF_SERVICE -> MemberException.NeedTermOfServiceException()
            ApiCode.REJOIN_RESTRICTED -> MemberException.RejoinRestrictedException()
            else -> Exception("알 수 없는 에러가 발생했습니다.")
        }
    }
}