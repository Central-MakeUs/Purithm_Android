package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.auth.JoinKakaoResponseDto
import com.cmc.purithm.data.remote.dto.base.BaseResponse

internal fun BaseResponse<JoinKakaoResponseDto>.toDomain() = this.data?.accessToken ?: ""
internal fun BaseResponse<Boolean>.toDomain() = this.data ?: false