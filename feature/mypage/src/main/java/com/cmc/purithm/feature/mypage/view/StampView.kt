package com.cmc.purithm.feature.mypage.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.ViewStampBinding
import kotlin.math.max
import kotlin.math.min

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
        R.drawable.ic_stamp_premium_unlock,
        R.drawable.ic_stamp_heart_unlock,
        R.drawable.ic_stamp_star_unlock,
        R.drawable.ic_stamp_flower2_unlock,
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
        val repeatCount = min(stampCount, STAMP_COUNT)
        // 이미지를 unlock 상태로 변경
        for (i in 0 until repeatCount) {
            val view = findViewById<ImageView>(getImageView(i + 1))
            view.setImageResource(unlockImgList[i])
        }
    }


    private fun getImageView(position: Int): Int {
        return resources.getIdentifier("img_$position", "id", context.packageName)
    }

    companion object {
        private const val STAMP_COUNT = 8
    }
}