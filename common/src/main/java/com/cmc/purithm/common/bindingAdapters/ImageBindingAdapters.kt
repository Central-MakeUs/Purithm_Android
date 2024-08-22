package com.cmc.purithm.common.bindingAdapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cmc.purithm.common.R
import com.cmc.purithm.design.util.Util.dp

/**
 * 이미지에 사용되는 BindingAdapter
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
object ImageBindingAdapters {
    private const val TAG = "ImageBindingAdapters"

    /**
     * URL 형식의 이미지를 Glide로 설정
     *
     * @param url 이미지 URL
     * */
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.setImageByUrl(url: String?, ) {
        Log.d(TAG, "setImageByUrl: start")
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(com.cmc.purithm.design.R.color.grey_200)
            .thumbnail(
                Glide.with(this)
                    .load(R.raw.gif_loading)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .skipMemoryCache(false)
                    .override(30,30)
                    .fitCenter()
            )
            .into(this)

    }

    @BindingAdapter("imageGifRes")
    @JvmStatic
    fun ImageView.setImageByGifRes(imageGifRes: Int) {
        Glide.with(this)
            .load(imageGifRes)
            .diskCacheStrategy(DiskCacheStrategy.ALL) // 디스크 캐시 설정
            .skipMemoryCache(false) // 메모리 캐시 사용 여부
            .apply(RequestOptions().override(180, 180)) // 해상도 조절
            .into(this)
    }

    @BindingAdapter("imageRes")
    @JvmStatic
    fun ImageView.setImageByRes(res: Int) {
        setImageResource(res)
    }

    @JvmStatic
    @BindingAdapter("profile")
    fun ImageView.setProfile(profile: String?) {
        Glide.with(this)
            .load(profile)
            .placeholder(com.cmc.purithm.design.R.drawable.bg_image_placeholder)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    // 이미지 뷰의 scaleType과 width, height를 변경
                    // 이미지를 적용
                    with(this@setProfile){
                        setImageDrawable(resource)
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                        )
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    Log.d(TAG, "onLoadCleared: start")
                }
            })
    }
}