package com.cmc.purithm.feature.splash

import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.feature.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is SplashState.Error -> {
                                dismissLoadingDialog()
                                // TODO : 오류 Dialog 추가해야함
                                // FIXME : 401 에러 해결 시 navigate 삭제
                                (activity as NavigationAction).navigateLogin()
                            }

                            SplashState.Loading -> showLoadingDialog()
                            SplashState.Success -> dismissLoadingDialog()
                            SplashState.Initialize -> viewModel.checkFirstRun()
                            else -> {}
                        }
                    }
                }
                launch {
                    viewModel.sideEffect.collect { action ->
                        when (action) {
                            SplashSideEffect.NavigateToLogin -> (activity as NavigationAction).navigateLogin()
                            SplashSideEffect.NavigateToHome -> (activity as NavigationAction).navigateHome()
                            SplashSideEffect.NavigateToOnBoarding -> (activity as NavigationAction).navigateOnBoarding()
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {}

    override fun initView() {
        val content = activity?.findViewById<View>(android.R.id.content)
        content?.viewTreeObserver?.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                // 토큰 검증에 성공했거나, 첫 실행이라면 화면 전환
                return when (viewModel.state.value) {
                    // FIXME : 오류 상태 삭제(401 에러 해결 시)
                    is SplashState.Error, SplashState.Success, SplashState.IsFirstRun -> {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    }

                    else -> false
                }
            }
        })
    }
}