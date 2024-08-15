package com.cmc.purithm.feature.review.adapter

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmc.purithm.feature.review.Constants
import com.cmc.purithm.feature.review.ui.ReviewHistoryPictureFragment

class ReviewPictureAdapter(
    fragmentActivity: FragmentActivity,
    imgList: List<String>
) : FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = mutableListOf<Fragment>()

    init {
        imgList.forEach { img ->
            Log.d(TAG, "img = $img")
            fragmentList.add(ReviewHistoryPictureFragment().apply {
                arguments = bundleOf(Constants.BUNDLE_REVIEW_HISTORY_IMG_KEY to img)
            })
        }
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    companion object {
        private const val TAG = "FilterReviewPictureAdapter"
    }
}