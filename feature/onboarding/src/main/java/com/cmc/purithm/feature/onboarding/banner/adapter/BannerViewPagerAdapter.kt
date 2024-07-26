package com.cmc.purithm.feature.onboarding.banner.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmc.purithm.feature.onboarding.Constants
import com.cmc.purithm.feature.onboarding.banner.OnBoardingBannerFragment
import com.cmc.purithm.feature.onboarding.banner.OnBoardingBannerUiModel

class BannerViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val bannerList : List<OnBoardingBannerUiModel>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = bannerList.size

    override fun createFragment(position: Int): Fragment {
        return OnBoardingBannerFragment().apply {
            arguments = bundleOf(Constants.BUNDLE_ONBOARDING_BANNER_KEY to bannerList[position])
        }
    }
}