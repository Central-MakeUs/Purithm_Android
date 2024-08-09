package com.cmc.purithm.design.component.view

import android.content.Context
import android.util.AttributeSet
import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView

/**
 * 스크롤 종료 시 나오는 애니메이션 무효화
 * */
class PurithmRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    init{
        setEdgeEffect()
    }
    private fun setEdgeEffect(){
        this.edgeEffectFactory = object : EdgeEffectFactory(){
            override fun createEdgeEffect(view: RecyclerView, direction: Int) = object : EdgeEffect(view.context){
                override fun onPull(deltaDistance: Float) {}
                override fun onRelease() {}
                override fun isFinished(): Boolean {
                    return true
                }
            }
        }
    }
}