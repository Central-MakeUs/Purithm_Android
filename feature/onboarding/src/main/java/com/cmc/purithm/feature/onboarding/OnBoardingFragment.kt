package com.cmc.purithm.feature.onboarding

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.ui.base.BaseFragment
import com.cmc.purithm.common.ui.base.NavigationAction
import com.cmc.purithm.feature.onboarding.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {
    private val viewModel: OnBoardingViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_onboarding

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { sideEffect ->
                    when(sideEffect){
                        OnBoardingSideEffect.NavigateToLogin -> (activity as NavigationAction).navigateLogin()
                    }
                }
            }
        }
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initView() {

    }
}