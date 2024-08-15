package com.cmc.purithm.feature.filter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterReviewItemUiModel(
    val id : Long,
    val pureDegree : Int,
    val userName : String,
    val content : String,
    val userProfileUrl : String,
    val createdAt : String,
    val thumbnail : String,
    val pictures : List<String>
) : Parcelable