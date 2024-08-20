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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO : 최소 딜레이 1초로 설정
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
                                CommonDialogFragment.showDialog(
                                    content = getString(com.cmc.purithm.design.R.string.error_common),
                                    positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                    positiveClickEvent = {
                                        requireActivity().finish()
                                    },
                                    fragmentManager = childFragmentManager
                                )
                            }

                            SplashState.Loading -> showLoadingDialog()
                            SplashState.Success -> dismissLoadingDialog()
                            SplashState.Initialize -> {
                                delay(1000)
                                viewModel.checkFirstRun()
                            }
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
                            SplashSideEffect.NavigateToTermOfService -> (activity as NavigationAction).navigateTermOfService()
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {}

    override fun initView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        //
    }
}