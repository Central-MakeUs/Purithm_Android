package com.cmc.purithm.common.bindingAdapters

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.core.view.forEach
import androidx.databinding.BindingAdapter
import com.cmc.purithm.common.R
import com.cmc.purithm.common.view.DelayClickEventListener
import com.cmc.purithm.design.component.view.FilterPremiumView

/**
 * 버튼에 공통적으로 사용되는 BindingAdapter
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
object ViewBindingAdapters {
    /**
     * 클릭 이벤트 (default로 Delay 설정)
     *
     * @param listener 실제 View가 클릭됐을 때, 동작되는 Listener
     * */
    @BindingAdapter(value = ["delayClickEvent"])
    @JvmStatic
    fun View.addDelayClickEvent(listener: View.OnClickListener) {
        this.setOnClickListener(DelayClickEventListener(onClickListener = listener))
    }

    @BindingAdapter(value = ["clickEvent"])
    @JvmStatic
    fun View.addButtonClickEvent(listener: View.OnClickListener) {
        this.setOnClickListener(DelayClickEventListener(onClickListener = listener, delayMillis = 0))
    }

    @BindingAdapter("touchEvent")
    @JvmStatic
    fun View.setTouchEventListener(touchEvent: () -> Unit) {
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    performClick()
                    isPressed = true
                    touchEvent()
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isPressed = false
                    touchEvent()
                    true
                }
                else -> false
            }
        }
    }
}