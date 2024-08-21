package com.cmc.purithm.feature.mypage.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.feature.mypage.R

object MyPageBindingAdapters {
    @JvmStatic
    @BindingAdapter("stampCount")
    fun TextView.setTextWithStampCount(stampCount: Int) {
        val premiumCnt = 8
        val premiumPlusCnt = 16

        text = when (stampCount) {
            in 0..7 -> {
                val remainCnt = premiumCnt - stampCount
                "${remainCnt}개 더 모으면 premium 필터를 열람할 수 있어요"
            }

            in 8..15 -> {
                val remainCnt = premiumPlusCnt - stampCount
                "${remainCnt}개 더 모으면 premium+ 필터를 열람할 수 있어요"
            }

            else -> {
                "premium 필터와 premium+ 필터 모두 열람할 수 있어요"
            }
        }
    }

    @JvmStatic
    @BindingAdapter("socialImg")
    fun ImageView.setSocialImg(socialImg: String?) {
        when (socialImg) {
            "KAKAO" -> setImageResource(R.drawable.ic_kakao)
        }
    }

    @JvmStatic
    @BindingAdapter("socialText")
    fun TextView.setSocialText(socialText: String?) {
        text = when (socialText) {
            "KAKAO" -> "카카오 로그인"
            else -> ""
        }

    }
}