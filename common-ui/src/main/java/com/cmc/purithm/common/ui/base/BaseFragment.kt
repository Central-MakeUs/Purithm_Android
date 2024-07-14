package com.cmc.purithm.common.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.cmc.purithm.common.ui.dialog.LoadingDialogFragment
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

    private val mLoadingDialog by lazy {
        LoadingDialogFragment()
    }

    private var mToast: Toast? = null
    private var mSnackBar : Snackbar? = null

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
        if(mLoadingDialog.isAdded){
            mLoadingDialog.dismissAllowingStateLoss()
        }
    }

    /**
     * 로딩 다이얼로그 show
     * */
    protected fun showLoadingDialog() {
        if(mLoadingDialog.isHidden){
            mLoadingDialog.show(childFragmentManager, LoadingDialogFragment::class.java.simpleName)
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
     * 스낵바 메시지 출력
     *
     * @param message 스낵바를 생성할 메시지
     * */
    protected fun showSnackBar(message: String) {
        mSnackBar?.dismiss()
        mSnackBar = Snackbar.make(requireContext(), binding.root, message, Snackbar.LENGTH_SHORT).apply {
            show()
        }
    }
}