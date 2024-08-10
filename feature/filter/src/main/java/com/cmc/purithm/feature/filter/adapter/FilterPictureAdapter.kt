package com.cmc.purithm.feature.filter.adapter

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cmc.purithm.domain.entity.filter.FilterImg
import com.cmc.purithm.feature.filter.Constants
import com.cmc.purithm.feature.filter.model.FilterImgType
import com.cmc.purithm.feature.filter.ui.FilterPictureFragment

class FilterPictureAdapter(
    fragmentActivity: FragmentActivity,
    pictureList: List<FilterImg>
) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = mutableListOf<Fragment>()

    init {
        pictureList.forEach { picture ->
            fragmentList.add(FilterPictureFragment().apply {
                arguments = bundleOf(Constants.BUNDLE_FILTER_IMG_KEY to picture)
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
        private const val TAG = "FilterPictureAdapter"
    }
}