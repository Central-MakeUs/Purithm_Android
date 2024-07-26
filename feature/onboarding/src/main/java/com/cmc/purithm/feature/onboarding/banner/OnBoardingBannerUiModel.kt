package com.cmc.purithm.feature.onboarding.banner

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OnBoardingBannerUiModel(
    val bannerImgRes : Int,
    val title : String,
    val content : String
) : Parcelable
