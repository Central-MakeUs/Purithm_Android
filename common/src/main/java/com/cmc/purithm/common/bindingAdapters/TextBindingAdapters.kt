package com.cmc.purithm.common.bindingAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextBindingAdapters {
    private val KOREAN_PATTERN = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*".toRegex()
    private val ENGLISH_PATTERN = ".*[a-zA-Z]+.*".toRegex()
    private val MIXED_PATTERN = ".*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]+.*".toRegex()
    private val SPECIAL_PATTERN = "^[^가-힣a-zA-Z0-9]+$".toRegex()

    /**
     * 한영 폰트을 자동으로 설정
     *
     * 적용할 한/영 폰트의 스타일을 넣어주면 자동으로 변경
     * */
    @JvmStatic
    @BindingAdapter(value = ["p_text, kr_style", "en_style"], requireAll = true)
    fun TextView.setPurithmText(text: String, krStyle: Int, enStyle: Int) {
        when {
            KOREAN_PATTERN.matches(text) -> setTextAppearance(krStyle)
            ENGLISH_PATTERN.matches(text) -> setTextAppearance(enStyle)
            MIXED_PATTERN.matches(text) -> setTextAppearance(krStyle)
            SPECIAL_PATTERN.matches(text) -> setTextAppearance(krStyle)
        }
        this.text = text
    }
}