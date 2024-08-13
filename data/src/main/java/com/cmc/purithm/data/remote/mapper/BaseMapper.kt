package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.base.BaseResponse

internal fun BaseResponse<Boolean>.checkSuccess() = this.data ?: false
internal fun BaseResponse<Long>.checkSuccess() = this.data ?: 0
internal fun BaseResponse<String>.checkSuccess() = this.data ?: ""