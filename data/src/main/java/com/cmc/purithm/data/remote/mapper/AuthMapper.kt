package com.cmc.purithm.data.remote.mapper

import com.cmc.purithm.data.remote.dto.BaseResponse
import com.cmc.purithm.data.remote.dto.auth.ResponseAuthKakao
import com.cmc.purithm.domain.entity.Member

internal fun BaseResponse<ResponseAuthKakao>.toDomain() = Member(id = 1L)