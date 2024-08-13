package com.cmc.purithm.data.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object RetrofitUtil {
    fun getProfileMultipartData(file : File): MultipartBody.Part {
        return file.run {
            val mediaType = "image/*".toMediaTypeOrNull()
            val requestFile = file.asRequestBody(mediaType)
            MultipartBody.Part.createFormData("profile_img", file.name, requestFile)
        }
    }
}