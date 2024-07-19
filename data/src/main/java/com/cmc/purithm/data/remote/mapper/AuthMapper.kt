package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.BaseResponse

internal fun BaseResponse<String>.toDomain() = this.data ?: ""
internal fun BaseResponse<Boolean>.toDomain() = this.data ?: false