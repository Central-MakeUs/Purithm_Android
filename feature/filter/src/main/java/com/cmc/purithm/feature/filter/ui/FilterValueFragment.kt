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
import com.cmc.purithm.domain.entity.filter.Filter
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
                                binding.viewAppbar.setLike(state.result)
                            }

                            FilterValueState.Loading -> {
                                showLoadingDialog()
                            }

                            is FilterValueState.Success -> {
                                dismissLoadingDialog()
                                setAppbar(state.data.name, state.data.liked)
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
                viewModel.requestFilterLike(filterId, liked)
            }
        )
    }
}