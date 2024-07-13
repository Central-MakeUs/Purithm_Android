package com.cmc.purithm.feature.splash

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import com.cmc.purithm.common.ui.base.BaseFragment
import com.cmc.purithm.common.ui.base.NavigationAction
import com.cmc.purithm.feature.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        // FIXME : 토큰 유효성 검증 등 확인 로직 이후로 변경
        Handler(Looper.getMainLooper()).postDelayed({
            (activity as NavigationAction).navigateSplashToLogin()
        }, 2000)
    }
}