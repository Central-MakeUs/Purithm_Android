package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse
import com.cmc.purithm.data.remote.dto.picture.GetUploadUrlResponseDto

internal fun BaseResponse<GetUploadUrlResponseDto>.toDomain() : String{
    return this.data?.url ?: ""
}