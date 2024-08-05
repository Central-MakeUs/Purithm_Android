package com.cmc.purithm.feature.home

import android.util.Log
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.home.adpater.HomeFilterAdapter
import com.cmc.purithm.feature.home.dialog.HomeItemFilterDialogFragment
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// TODO : tag에 대한 정보 나오면 확인하기
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()

    private val filterSortTypeArray by lazy { resources.getStringArray(R.array.category_filter_sort) }
    private var lastCheckedId = -1
    private var selectedSortIndex = 0
    private val homeFilterAdapter by lazy {
        HomeFilterAdapter(object : HomeFilterAdapter.HomeFilterItemClickListener {
            override fun onItemClick(id: Long) {
                Log.e(TAG, "onItemClick: on!")
            }

            override fun onLikeClick(id: Long) {
                Log.e(TAG, "onLikeClick: on!")
            }
        })
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.action.collect { action ->
                        when (action) {
                            HomeAction.ClickFilter -> {}
                            HomeAction.ClickFilterSort -> showFilterSortDialog()
                        }
                    }
                }
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            HomeState.Initialize -> viewModel.getFilters()
                            HomeState.Loading -> showLoadingDialog()
                            is HomeState.Error -> {
                                dismissLoadingDialog()
                                showToast(state.msg)
                            }

                            is HomeState.Success -> {
                                dismissLoadingDialog()
                                updateFilters(state.data)
                            }
                        }
                    }
                }
                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is HomeSideEffect.NavigateToFilter -> (activity as NavigationAction).navigateFilterItem(
                                sideEffect.id
                            )
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initView() {
        with(binding) {
            listFilter.adapter = homeFilterAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = getString(R.string.title_appbar)
            )
            tvItemFilter.text = filterSortTypeArray[selectedSortIndex]
        }
        initRadioGroup()
    }

    /**
     * 라디오 버튼 그룹을 Chip으로 사용하기 위해 커스텀
     * */
    private fun initRadioGroup() {
        with(binding.groupFilterKeyword) {
            forEach { view ->
                if (view is RadioButton) {
                    view.setOnClickListener {
                        if (view.id == lastCheckedId) {
                            clearCheck()
                            lastCheckedId = -1
                        } else {
                            view.isChecked = true
                            lastCheckedId = view.id
                        }
                    }
                }
            }
        }
    }

    private fun showFilterSortDialog(){
        HomeItemFilterDialogFragment(
            currentSortIndex = selectedSortIndex,
            clickEvent = {selectedIndex ->
                selectedSortIndex = selectedIndex
                binding.tvItemFilter.text = filterSortTypeArray[selectedSortIndex]
                viewModel.getFilters(sortedBy = FILTER_SORT_TYPE[selectedSortIndex])
            }
        ).show(childFragmentManager, null)
    }

    private fun updateFilters(list: PagingData<HomeFilterUiModel>) {
        viewLifecycleOwner.lifecycleScope.launch {
            homeFilterAdapter.submitData(list)
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
        // FIXME : 필터 정렬 string 확인
        private val FILTER_SORT_TYPE = arrayOf("popular", "latest", "latest")
    }
}