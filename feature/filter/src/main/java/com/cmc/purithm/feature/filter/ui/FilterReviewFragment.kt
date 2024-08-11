package com.cmc.purithm.feature.filter.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.design.util.Util
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.adapter.FilterReviewListAdapter
import com.cmc.purithm.feature.filter.databinding.FragmentFilterReviewBinding
import com.cmc.purithm.feature.filter.dialog.FilterReviewGuideBottomDialog
import com.cmc.purithm.feature.filter.viewmodel.FilterReviewSideEffects
import com.cmc.purithm.feature.filter.viewmodel.FilterReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterReviewFragment : BaseFragment<FragmentFilterReviewBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_review

    private val viewModel: FilterReviewViewModel by viewModels()
    private val navArgs by navArgs<FilterReviewFragmentArgs>()
    private val filterId by lazy { navArgs.filterId }
    private val filterReviewAdapter by lazy { FilterReviewListAdapter() }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        if (state.loading) {
                            showLoadingDialog()
                        } else {
                            dismissLoadingDialog()
                        }

                        if (state.data != null) {
                            Log.d(TAG, "initObserving: submit = ${state.data.reviews}")
                            filterReviewAdapter.submitList(state.data.reviews)
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is FilterReviewSideEffects.NavigateFilterReviewDetail -> TODO()
                            FilterReviewSideEffects.ShowFilterReviewGuideDialog -> FilterReviewGuideBottomDialog().show(
                                childFragmentManager,
                                null
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
        viewModel.getFilterReview(filterId)
        with(binding) {
            listReview.adapter = filterReviewAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_DEFAULT,
                title = "",
                questionClickListener = {
                    viewModel.clickFilterReviewGuide()
                },
                backClickListener = {
                    findNavController().popBackStack()
                }
            )

            btnConfirm.setOnClickListener {
                requireView().showSnackBar(
                    message = "준비중입니다"
                )
            }
        }
    }

    companion object {
        private const val TAG = "FilterReviewFragment"
    }
}