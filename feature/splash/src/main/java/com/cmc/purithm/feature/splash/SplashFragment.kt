package com.cmc.purithm.feature.splash

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import com.cmc.purithm.common.ui.base.BaseFragment
import com.cmc.purithm.common.ui.base.NavigationAction
import com.cmc.purithm.feature.splash.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            // FIXME : 임시 Splash
            launch {
                viewModel.test()
            }
        }

        val content = activity?.findViewById<View>(android.R.id.content)
        content?.viewTreeObserver?.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if(viewModel.state.value){
                    // content is ready
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    (activity as NavigationAction).navigateSplashToLogin()
                    true
                }else {
                    false
                }
            }
        })
    }
}