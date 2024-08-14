package com.cmc.purithm.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object RetrofitUtil {
    fun getMultipartData(key : String, file : File): MultipartBody.Part {
        return file.run {
            val mediaType = "image/*".toMediaTypeOrNull()
            val requestFile = file.asRequestBody(mediaType)
            MultipartBody.Part.createFormData(key, file.name, requestFile)
        }
    }

    fun getRequestBody(file : File): RequestBody {
        return file.run {
            val mediaType = "image/*".toMediaTypeOrNull()
            file.asRequestBody(mediaType)
        }
    }
}