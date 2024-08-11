package com.cmc.purithm.feature.filter.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.adapter.FilterIntroductionAdapter
import com.cmc.purithm.feature.filter.databinding.FragmentFilterIntroductionBinding
import com.cmc.purithm.feature.filter.viewmodel.FilterIntroductionSideEffect
import com.cmc.purithm.feature.filter.viewmodel.FilterIntroductionState
import com.cmc.purithm.feature.filter.viewmodel.FilterIntroductionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterIntroductionFragment : BaseFragment<FragmentFilterIntroductionBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_introduction

    private val navArgs by navArgs<FilterIntroductionFragmentArgs>()
    private val filterId by lazy { navArgs.filterId }
    private val viewModel by viewModels<FilterIntroductionViewModel>()
    private val filterIntroductionAdapter by lazy { FilterIntroductionAdapter() }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is FilterIntroductionState.Error -> {
                                dismissLoadingDialog()
                                CommonDialogFragment.showDialog(
                                    content = getString(com.cmc.purithm.design.R.string.error_common),
                                    positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                    positiveClickEvent = {
                                        requireActivity().finish()
                                    },
                                    fragmentManager = childFragmentManager
                                )
                            }

                            FilterIntroductionState.Initialize -> viewModel.getFilterIntroduction(
                                filterId
                            )

                            FilterIntroductionState.Loading -> showLoadingDialog()
                            is FilterIntroductionState.Success -> {
                                dismissLoadingDialog()
                                binding.data = state.data
                                filterIntroductionAdapter.submitList(state.data.photoDescriptions)
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            // 작가 Shop으로 이동
                            is FilterIntroductionSideEffect.NavigateArtistShop -> TODO()
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
        with(binding){
            listFilterDetail.adapter = filterIntroductionAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "필터 소개",
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
        }
    }

    companion object {
        private const val TAG = "FilterIntroductionFragm"
    }
}