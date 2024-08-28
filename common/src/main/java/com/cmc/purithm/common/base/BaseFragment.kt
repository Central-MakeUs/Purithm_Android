package com.cmc.purithm.common.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.common.dialog.LoadingDialogFragment
import com.cmc.purithm.design.util.Util.showPurithmSnackBar
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment base 코드
 *
 * @author Yu Seung Woo
 * @since 2024-07-05
 * */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    private var _binding: T? = null
    val binding get() = requireNotNull(_binding)
    abstract val layoutId: Int

    /**
     * ViewModel에서 State나 Action을 observe
     * */
    abstract fun initObserving()

    /**
     * DataBinding에 필요한 변수 설정
     * */
    abstract fun initBinding()

    /**
     * XML이 아닌 코드에서 View를 설정
     * */
    abstract fun initView()

    private val mLoadingDialog = LoadingDialogFragment()

    private var mToast: Toast? = null
    private var mCommonDialog: CommonDialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserving()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mToast?.cancel()
    }

    /**
     * 로딩 다이얼로그 dismiss
     * */
    protected fun dismissLoadingDialog() {
        Log.d(TAG, "dismissLoadingDialog: start")
        Log.d(TAG, "dismissLoadingDialog: isVisible = ${mLoadingDialog.isVisible}")
        if (mLoadingDialog.isVisible) {
            mLoadingDialog.dismissAllowingStateLoss()
        }
    }

    /**
     * 로딩 다이얼로그 show
     * */
    protected fun showLoadingDialog() {
        Log.d(TAG, "showLoadingDialog: start")
        Log.d(TAG, "showLoadingDialog: isVisible = ${mLoadingDialog.isVisible}")
        if (!mLoadingDialog.isVisible) {
            mLoadingDialog.show(childFragmentManager, mLoadingDialog.tag)
        }
    }

    /**
     * 토스트 메시지 출력
     *
     * @param message 토스트를 생성할 메시지
     * */
    protected fun showToast(message: String) {
        mToast?.cancel()
        mToast = Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).apply {
            show()
        }
    }

    /**
     * 프래그먼트 navigate
     *
     * @param direction 다음 프래그먼트로 이동할 Direction
     * */
    protected fun navigate(direction: NavDirections) {
        val controller = findNavController()
        controller.navigate(direction)
    }

    /**
     * 스낵바 메시지 출력
     *
     * @param message 스낵바를 생성할 메시지
     * */
    protected fun showSnackBar(
        view: View,
        message: String,
        actionString: String = "",
        action: (() -> Unit)? = null,
        topVisible : Boolean = false
    ) {
        showPurithmSnackBar(view, message, actionString, action, topVisible)
    }

    companion object {
        private const val TAG = "BaseFragment"
    }
}