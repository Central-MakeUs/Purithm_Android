package com.cmc.purithm.feature.review.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.cmc.purithm.feature.review.R
import com.cmc.purithm.feature.review.databinding.ViewReviewPictureBinding

class ReviewPictureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding by lazy { initView(context) }
    private var index = -1

    private fun initView(context: Context): ViewReviewPictureBinding {
        val inflater = LayoutInflater.from(context)
        return DataBindingUtil.inflate(inflater, R.layout.view_review_picture, this, true)
    }

    /**
     * 사진 정보 추가
     *
     * @param url 등록된 url
     * @param index 사진들을 갖고 있는 list의 index
     * @param deleteEvent 삭제될 때 event, 기본적으로 removeView를 갖고있음
     * */
    fun setInfo(url: String, index: Int, deleteEvent: (Int) -> Unit) {
        this.index = index
        with(binding) {
            this.url = url
            // view가 갖고있는 index를 return
            btnDelete.setOnClickListener {
                deleteEvent(index)
                removeView(this@ReviewPictureView)
            }
        }
    }

}