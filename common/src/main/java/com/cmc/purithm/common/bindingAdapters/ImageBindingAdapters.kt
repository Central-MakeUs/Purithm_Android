package com.cmc.purithm.common.bindingAdapters

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.cmc.purithm.common.R

/**
 * 이미지에 사용되는 BindingAdapter
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
object ImageBindingAdapters {
    /**
     * URL 형식의 이미지를 Glide로 설정
     *
     * @param url 이미지 URL
     * */
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.setImageByUrl(url: String?) {
        Glide.with(this)
            .load(url)
            .placeholder(com.cmc.purithm.design.R.drawable.bg_image_placeholer)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("imageRes")
    fun ImageView.setImageByRes(res: Int) {
        Glide.with(this)
            .load(res)
            .placeholder(com.cmc.purithm.design.R.drawable.bg_image_placeholer)
            .into(this)
    }

    /**
     * 찜 클릭
     * */
    @JvmStatic
    @BindingAdapter("liked")
    fun ImageButton.setLiked(isLiked: Boolean) {
        setImageResource(if (isLiked) com.cmc.purithm.design.R.drawable.ic_like_pressed else com.cmc.purithm.design.R.drawable.ic_like_unpressed)
    }
}