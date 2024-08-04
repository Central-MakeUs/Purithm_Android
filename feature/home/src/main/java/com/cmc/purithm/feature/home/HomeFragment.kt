package com.cmc.purithm.feature.home

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purithm.feature.home.adpater.HomeFilterAdapter
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.internal.synchronizedImpl
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()
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
        }
    }

    private fun updateFilters(list: PagingData<HomeFilterUiModel>) {
        viewLifecycleOwner.lifecycleScope.launch {
            homeFilterAdapter.submitData(list)
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}