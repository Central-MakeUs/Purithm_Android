package com.cmc.purithm.feature.onboarding.banner

import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.feature.onboarding.Constants
import com.cmc.purithm.feature.onboarding.R
import com.cmc.purithm.feature.onboarding.databinding.FragmentOnboardingBannerBinding
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.Config
import java.lang.IllegalStateException

@AndroidEntryPoint
class OnBoardingBannerFragment : BaseFragment<FragmentOnboardingBannerBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_onboarding_banner

    private val bannerImgRes by lazy {
        arguments?.getInt(Constants.BUNDLE_ONBOARDING_BANNER_IMG_KEY) ?: throw IllegalStateException(getString(
            com.cmc.purithm.design.R.string.error_common))
    }

    override fun initObserving() {}

    override fun initBinding() {
        binding.imgRes = bannerImgRes
    }
    override fun initView() {}
}