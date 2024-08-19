package com.cmc.purithm.feature.mypage.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.ViewStampBinding

class StampView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy {
        initView(context)
    }

    private val unlockImgList = listOf(
        R.drawable.ic_stamp_flower_unlock,
        R.drawable.ic_stamp_cloud_unlock,
        R.drawable.ic_stamp_glow_unlock,
        R.drawable.ic_stamp_clover_unlock,
        R.drawable.ic_stamp_heart_unlock,
        R.drawable.ic_stamp_star_unlock,
        R.drawable.ic_stamp_flower2_unlock,
        R.drawable.ic_stamp_premium_unlock,
        R.drawable.ic_stamp_premium_plus_unlock
    )

    private fun initView(context: Context): ViewStampBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_stamp,
            this,
            true
        )
    }

    fun setStampCount(stampCount: Int) {
        binding.stampCount = stampCount
        /*
        * isCurrentPremium value
        *
        * 0 -> 프리미엄 필요
        * 1 -> 프리미엄+ 필요
        * 2 -> 프리미엄+ 상태
        * */
        val isCurrentPremium = stampCount / STAMP_COUNT
        var repeatCnt = (stampCount % STAMP_COUNT)
        // 모든 도장을 모은 경우 (16개)
        if(isCurrentPremium == 2){
            repeatCnt += STAMP_COUNT
        }
        // 이미지를 unlock 상태로 변경
        for (i in 0 until repeatCnt) {
            val view = findViewById<ImageView>(getImageView(i + 1))
            view.setImageResource(unlockImgList[i])
        }
        // 만약 프리미엄+ 상태라면 마지막 이미지 변경
        if(isCurrentPremium == 2){
            binding.img8.setImageResource(unlockImgList[8])
        }
    }


    private fun getImageView(position: Int): Int {
        return resources.getIdentifier("img_$position", "id", context.packageName)
    }

    companion object {
        private const val STAMP_COUNT = 8
    }
}