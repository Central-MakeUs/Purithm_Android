package com.cmc.purithm.feature.mypage.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.adapter.FilterLikeAdapter
import com.cmc.purithm.feature.mypage.databinding.FragmentFavoriteFilterBinding
import com.cmc.purithm.feature.mypage.viewmodel.FilterLikeSideEffects
import com.cmc.purithm.feature.mypage.viewmodel.FilterLikeState
import com.cmc.purithm.feature.mypage.viewmodel.FilterLikeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyFavoriteFilterFragment : BaseFragment<FragmentFavoriteFilterBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_favorite_filter
    private val viewModel by viewModels<FilterLikeViewModel>()
    private val filterLikeAdapter by lazy {
        FilterLikeAdapter(viewModel)
    }
    private var likeSize = 0

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            FilterLikeState.DeleteLikeSuccess -> {
                                dismissLoadingDialog()
                                likeSize -= 1
                                binding.tvTotalCount.text = String.format(
                                    getString(R.string.content_stamp_total_count),
                                    likeSize
                                )
                            }

                            is FilterLikeState.Error -> {
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

                            FilterLikeState.Initialized -> viewModel.getFilterLike()
                            FilterLikeState.Loading -> showLoadingDialog()
                            FilterLikeState.SetLikeSuccess -> {
                                dismissLoadingDialog()
                                likeSize += 1
                                binding.tvTotalCount.text = String.format(
                                    getString(R.string.content_stamp_total_count),
                                    likeSize
                                )
                            }

                            is FilterLikeState.Success -> {
                                dismissLoadingDialog()
                                likeSize = state.data.size
                                binding.count = likeSize
                                filterLikeAdapter.submitList(state.data)
                            }

                            FilterLikeState.FailFilterLike -> {
                                showSnackBar(binding.root, message = "오류가 발생했습니다")
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            is FilterLikeSideEffects.NavigateFilter -> (activity as NavigationAction).navigateFilterItem(
                                sideEffect.id,
                                false
                            )
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {

    }

    override fun initView() {
        with(binding) {
            listFilterLike.adapter = filterLikeAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.KR_BACK,
                title = "찜 목록",
                backClickListener = {
                    findNavController().popBackStack()
                }
            )
        }
    }
}