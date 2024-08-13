package com.cmc.purithm.data.repository

import com.cmc.purithm.data.remote.HandleApi
import com.cmc.purithm.data.remote.mapper.checkSuccess
import com.cmc.purithm.data.remote.service.PictureService
import com.cmc.purithm.data.remote.service.S3Service
import com.cmc.purithm.data.util.RetrofitUtil
import com.cmc.purithm.domain.repository.PictureRepository
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

internal class PictureRepositoryImpl @Inject constructor(
    private val pictureService: PictureService,
    private val s3Service: S3Service
) : PictureRepository {
    override suspend fun getPictureUploadUrl(prefix: String): String {
        return HandleApi.callApi { pictureService.getPictureUploadUrl(prefix).checkSuccess() }
    }

    override suspend fun uploadPicture(url: String, file : File): Int {
        val multipart = RetrofitUtil.getProfileMultipartData(file)
        return s3Service.uploadPictureToS3(url, multipart)
    }
}