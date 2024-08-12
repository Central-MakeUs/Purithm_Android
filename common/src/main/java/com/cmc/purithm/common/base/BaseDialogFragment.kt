package com.cmc.purithm.common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

/**
 * DialogFragment Base 코드
 *
 * @since 2024-07-14
 * @author Yu Seung Woo
 * */
abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {
    private var _binding: T? = null
    val binding get() = requireNotNull(_binding)
    private val windowManager by lazy { context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager }
    abstract val layoutResourceId: Int
    abstract val dialogType: BaseDialogType

    abstract fun initDataBinding()
    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = this

        dialog?.window?.run {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            requestFeature(Window.FEATURE_NO_TITLE)
        }

        initDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        setDialogType(dialogType)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setDialogType(type: BaseDialogType) {
        if (type == BaseDialogType.WRAP) {
            setWrapDialog()
        } else {
            setFullSizeDialog()
        }
    }

    private fun setWrapDialog() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        val params = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params
    }

    private fun setFullSizeDialog() {
        dialog?.window?.run {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
    }

    enum class BaseDialogType {
        WRAP, FULL_SIZE
    }
}