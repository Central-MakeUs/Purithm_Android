package com.cmc.purithm.data.remote

import com.cmc.purithm.data.remote.dto.BaseResponse
import com.cmc.purithm.domain.exception.AuthException
import com.cmc.purithm.domain.exception.MemberException
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.HttpException

internal object HandleApi {
    private val gson = Gson()
    inline fun <T> BaseResponse<T>.callApi(
        mapper: (BaseResponse<T>) -> T
    ) = try {
        mapper.invoke(this)
    } catch (e: HttpException) {
        e.printStackTrace()
        throw createException(e.response()?.errorBody().toString())
    } catch (e: Exception) {
        e.printStackTrace()
        throw Exception("알 수 없는 에러가 발생했습니다.")
    }

    // 앱에서 사용되는 Exception
    private fun createException(errorBody: String?) : Exception {
        val errorResponse = gson.fromJson(errorBody, BaseResponse::class.java)
        return when(errorResponse.code){
            ApiCode.TOKEN_EXPIRE -> AuthException.ExpireTokenException(errorResponse.message)
            ApiCode.TOKEN_VALIDATE_FAIL -> AuthException.InvalidTokenException(errorResponse.message)
            ApiCode.NEED_TERM_OF_SERVICE -> MemberException.NeedTermOfServiceException()
            else -> Exception("알 수 없는 에러가 발생했습니다.")
        }
    }
}