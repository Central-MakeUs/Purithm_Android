package com.cmc.purithm.common.bindingAdapters

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.setImageByUrl(url: String?) {
        Glide.with(this)
            .load(url)
            .placeholder(com.cmc.purithm.design.R.drawable.bg_image_placeholder)
            .into(this)
    }

    @BindingAdapter("imageRes")
    @JvmStatic
    fun ImageView.setImageByRes(res: Int) {
        setImageResource(res)
    }
}