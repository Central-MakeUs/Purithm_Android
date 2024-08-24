package com.cmc.purithm.feature.mypage.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.cmc.purithm.common.util.getColorResource
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
    @BindingAdapter("stampCount2")
    fun TextView.setTextWithStampCount2(stampCount: Int) {
        val premiumCnt = 8
        val premiumPlusCnt = 16

        text = when (stampCount) {
            in 0..7 -> {
                val remainCnt = premiumCnt - stampCount
                "premium까지 ${remainCnt}개 남음"
            }

            in 8..15 -> {
                val remainCnt = premiumPlusCnt - stampCount
                "premium+까지 ${remainCnt}개 남음"
            }

            else -> {
                ""
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

    @JvmStatic
    @BindingAdapter("dotDate")
    fun TextView.setDotDate(dotDate: String?) {
        text = dotDate?.replace("-", ".")
    }

    @JvmStatic
    @BindingAdapter("stampMembership")
    fun TextView.setStampMembership(stampMembership: String) {
        when (stampMembership) {
            "PREMIUM" -> {
                text = "premium"
                setTextColor(context.getColorResource(com.cmc.purithm.design.R.color.blue_400))
                setBackgroundResource(com.cmc.purithm.design.R.drawable.shape_badge_outline_blue_400)
            }

            "PREMIUM+" -> {
                text = "premium+"
                setTextColor(context.getColorResource(com.cmc.purithm.design.R.color.purple_500))
                setBackgroundResource(com.cmc.purithm.design.R.drawable.shape_badge_outline_purple)
            }

            else -> {
                text = "basic"
                setTextColor(context.getColorResource(com.cmc.purithm.design.R.color.grey_400))
                setBackgroundResource(com.cmc.purithm.design.R.drawable.shape_badge_outline_blue_100)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("maxCount")
    fun TextView.setMaxCount(count: Int) {
        text = if (count < 100) {
            count.toString()
        } else {
            "99+"
        }
    }
}