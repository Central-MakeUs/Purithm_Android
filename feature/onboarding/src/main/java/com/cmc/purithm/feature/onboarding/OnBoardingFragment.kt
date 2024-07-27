package com.cmc.purithm.feature.onboarding

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.feature.onboarding.banner.adapter.BannerViewPagerAdapter
import com.cmc.purithm.feature.onboarding.databinding.FragmentOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>() {
    private val viewModel: OnBoardingViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_onboarding
    private val bannerTitleArray by lazy { resources.getStringArray(R.array.title_banner) }
    private val bannerContentArray by lazy { resources.getStringArray(R.array.content_banner) }
    private val bannerImgList by lazy { createBannerList() }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            OnBoardingSideEffect.NavigateToLogin -> (activity as NavigationAction).navigateLogin()
                            OnBoardingSideEffect.ShowErrorDialog -> {}
                        }
                    }
                }
                launch {
                    viewModel.action.collect { action ->
                        when (action) {
                            OnBoardingAction.ClickLoginButton -> viewModel.checkFirstRun()
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    override fun initView() {
        with(binding) {
            vpBanner.adapter = BannerViewPagerAdapter(
                requireActivity(),
                bannerImgList
            )
            vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.title = bannerTitleArray[position]
                    binding.content = bannerContentArray[position]
                }
            })
            indicatorOnboarding.setViewPager(vpBanner)
        }
    }

    private fun createBannerList(): List<Int> {
        val list = mutableListOf<Int>()
        bannerTitleArray.forEachIndexed { index, _ ->
            val imgRes = resources.getIdentifier("bg_onboarding_${index+1}", "drawable", requireContext().packageName)
            list.add(imgRes)
        }
        return list.toList()
    }
    companion object {
        private const val TAG = "OnBoardingFragment"
    }
}