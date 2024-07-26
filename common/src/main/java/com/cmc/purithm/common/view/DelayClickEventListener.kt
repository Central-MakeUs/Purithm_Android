package com.cmc.purithm.common.view

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 사용자가 버튼을 짧은 시간내에 중복해서 클릭을 방지 하기 위해 딜레이 설정
 *
 * @param delayMillis 지연 시간
 * @param onClickListener 실제 클릭 시 발생되는 이벤트
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
class DelayClickEventListener(
    private val delayMillis: Long = 1000,
    private val onClickListener: View.OnClickListener
) : View.OnClickListener {
    private val clickEnableState = AtomicBoolean(true)
    override fun onClick(v: View?) {
        if (clickEnableState.getAndSet(false)) {
            onClickListener.onClick(v)
            v?.postDelayed({
                clickEnableState.set(true)
            }, delayMillis)
        }
    }
}