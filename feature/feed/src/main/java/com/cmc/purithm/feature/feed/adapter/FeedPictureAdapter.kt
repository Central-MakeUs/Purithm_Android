package com.cmc.purithm.feature.feed.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmc.purithm.feature.feed.Constants
import com.cmc.purithm.feature.feed.ui.FeedPictureFragment

class FeedPictureAdapter(
    fragmentActivity: FragmentActivity,
    imgList: List<String>
) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = mutableListOf<Fragment>()

    init {
        imgList.forEach { img ->
            fragmentList.add(FeedPictureFragment().apply {
                arguments = bundleOf(Constants.BUNDLE_FEED_IMG_KEY to img)
            })
        }
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}