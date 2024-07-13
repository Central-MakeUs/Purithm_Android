package com.cmc.purithm.common.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Activity Base 코드
 *
 * @since 2024-07-14
 * @author Yu Seung Woo
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = requireNotNull(_binding)

    abstract val layoutResourceId: Int

    abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutResourceId)
        binding.lifecycleOwner = this

        init()
    }

    /**
     * Binding 객체가 destroy되도 남아있어 null로 변경
     * */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}