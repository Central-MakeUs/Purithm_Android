package com.cmc.purithm.feature.filter.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.domain.entity.filter.FilterValue
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.databinding.FragmentFilterValueBinding
import com.cmc.purithm.feature.filter.dialog.FilterValueGuideBottomDialog
import com.cmc.purithm.feature.filter.viewmodel.FilterValueSideEffects
import com.cmc.purithm.feature.filter.viewmodel.FilterValueState
import com.cmc.purithm.feature.filter.viewmodel.FilterValueViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterValueFragment : BaseFragment<FragmentFilterValueBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter_value

    private val navArgs by navArgs<FilterValueFragmentArgs>()
    private val viewModel: FilterValueViewModel by viewModels()
    private val filterId by lazy {
        navArgs.filterId
    }
    private var likeState = false

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is FilterValueState.Error -> {
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

                            FilterValueState.Initialize -> {
                                viewModel.getFilterValue(filterId)
                            }

                            is FilterValueState.LikeResult -> {
                                dismissLoadingDialog()
                                Log.d(TAG, "initObserving: state result = ${state.result}")
                                binding.viewAppbar.setLikeState(state.result)
                                likeState = state.result
                            }

                            FilterValueState.Loading -> {
                                showLoadingDialog()
                            }

                            is FilterValueState.Success -> {
                                dismissLoadingDialog()
                                likeState = state.data.liked
                                setAppbar(state.data.name, likeState)
                                setFilterValue(state.data.filterValue)
                                binding.imgUrl = state.data.thumbnail
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            FilterValueSideEffects.ShowFilterGuideDialog -> FilterValueGuideBottomDialog().show(
                                childFragmentManager,
                                null
                            )

                            FilterValueSideEffects.ShowFilterLikeSnackBar -> {
                                showSnackBar(
                                    view = requireView(),
                                    message = "찜 목록에 담겼어요",
                                    actionString = "찜 목록 보기",
                                    action = {
                                        (activity as NavigationAction).navigateMyFavoriteFilter()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    override fun initView() {}

    private fun setFilterValue(filterValue: FilterValue) {
        with(binding) {
            viewLightbalance.setFilterValue("lightBalance", filterValue.lightBalance)
            viewLightness.setFilterValue("brightness", filterValue.brightness)
            viewExposure.setFilterValue("exposure", filterValue.exposure)
            viewContrast.setFilterValue("contrast", filterValue.contrast)
            viewHighlight.setFilterValue("highlight", filterValue.highlight)
            viewShadow.setFilterValue("shadow", filterValue.shadow)
            viewSaturation.setFilterValue("saturation", filterValue.saturation)
            viewTint.setFilterValue("tint", filterValue.tint)
            viewWarming.setFilterValue("temperature", filterValue.temperature)
            viewSharpness.setFilterValue("clear", filterValue.clear)
            viewClarity.setFilterValue("clarity", filterValue.clarity)
        }
    }

    private fun setAppbar(title: String, liked: Boolean) {
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.ENG_DEFAULT,
            title = title,
            likeState = liked,
            backClickListener = {
                findNavController().popBackStack()
            },
            likeClickListener = {
                viewModel.requestFilterLike(filterId, likeState)
            }
        )
    }
    
    companion object{
        private const val TAG = "FilterValueFragment"
    }
}