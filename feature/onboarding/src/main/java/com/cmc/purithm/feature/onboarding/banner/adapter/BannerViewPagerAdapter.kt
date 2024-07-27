package com.cmc.purithm.feature.onboarding.banner.adapter

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmc.purithm.feature.onboarding.Constants
import com.cmc.purithm.feature.onboarding.banner.OnBoardingBannerFragment

class BannerViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val bannerImgList : List<Int>
) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = mutableListOf<Fragment>()
    // 사진 로딩 시간이 걸려 미리 인스턴스 생성
    init {
        Log.d(TAG, "init")
        bannerImgList.forEach { imgRes ->
            Log.d(TAG, "imgRes = $imgRes")
            fragmentList.add(OnBoardingBannerFragment().apply {
                arguments = bundleOf(Constants.BUNDLE_ONBOARDING_BANNER_IMG_KEY to imgRes)
            })
        }
    }
    override fun getItemCount() = bannerImgList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
    companion object {
        private const val TAG = "BannerViewPagerAdapter"
    }
}