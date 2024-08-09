package com.cmc.purithm.domain.entity.filter

import java.io.Serializable

data class FilterImg (
    val picture : String = "",
    val originalPicture : String = ""
) : Serializable