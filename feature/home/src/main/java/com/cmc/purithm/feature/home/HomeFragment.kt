package com.cmc.purithm.feature.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.domain.entity.filter.Filter
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(){
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val viewModel : HomeViewModel by viewModels()

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.action.collect {

                    }
                }
                launch {
                    viewModel.state.collect {

                    }
                }
                launch {
                    viewModel.sideEffect.collect {

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

    private fun updateFilters(list : PagingData<Filter>){
        viewLifecycleOwner.lifecycleScope.launch {
            // TODO 필터 아이템 submitData
        }
    }
}