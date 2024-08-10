package com.cmc.purithm.feature.filter.ui

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.domain.entity.filter.FilterImg
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.adapter.FilterPictureAdapter
import com.cmc.purithm.feature.filter.databinding.FragmentFilterBinding
import com.cmc.purithm.feature.filter.model.FilterImgType
import com.cmc.purithm.feature.filter.viewmodel.FilterSideEffects
import com.cmc.purithm.feature.filter.viewmodel.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter
    private val viewModel: FilterViewModel by activityViewModels()
    private val navArgs by navArgs<FilterFragmentArgs>()

    private val filterId by lazy { navArgs.filterId }
    private lateinit var pictureAdapter: FilterPictureAdapter
    private var selectedImgIndex = 0

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        Log.d(TAG, "initObserving: start")
                        Log.d(TAG, "initObserving: noText = ${state.noText}")
                        if (state.data != null) {
                            initAppBar(state.data.name, state.data.liked, state.data.likes)
                            initViewPager(state.data.pictures)
                        }
                        if (state.isFirst) {
                            with(binding.viewGuide) {
                                root.visibility = View.VISIBLE
                                btnGuideConfirm.setOnClickListener {
                                    binding.viewGuide.root.visibility = View.GONE
                                }
                            }
                        }
                        if (state.error != null) {
                            Log.e(TAG, "initObserving: error")
                            Log.e(TAG, "initObserving: msg = ${state.error.message}")
                            CommonDialogFragment.showDialog(
                                content = getString(com.cmc.purithm.design.R.string.error_common),
                                positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                positiveClickEvent = {
                                    requireActivity().finish()
                                },
                                fragmentManager = childFragmentManager
                            )
                        }
                    }
                }

                launch {
                    // TODO : 리뷰 페이지 서버 연동 후 구현
                    viewModel.sideEffect.collect { sideEffect ->
                        Log.d(TAG, "initObserving: sideEffect on")
                        when (sideEffect) {
                            is FilterSideEffects.NavigateFilterIntroduction -> navigate(FilterFragmentDirections.actionFilterFragmentToFilterIntroductionFragment(
                                sideEffect.filterId
                            ))

                            is FilterSideEffects.NavigateFilterReview -> {
                                TODO()
                            }

                            is FilterSideEffects.NavigateFilterLoading -> navigate(FilterFragmentDirections.actionFilterFragmentToFilterLoadingFragment(
                                sideEffect.filterId
                            ))
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
        viewModel.getFilterDetail(filterId)
    }

    private fun initAppBar(title: String, liked: Boolean, likes: Int) {
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.ENG_LIKE,
            title = title,
            likeState = liked,
            likeCnt = likes,
            backClickListener = {
                (activity as NavigationAction).navigateHome()
            },
            likeClickListener = {
                if(viewModel.state.value.data?.liked == true){
                    viewModel.deleteFilterLike(filterId)
                } else {
                    viewModel.requestFilterLike(filterId)
                }
            }
        )
    }

    private fun initViewPager(pictureList: List<FilterImg>) {
        Log.d(TAG, "initViewPager: start")
        if (!::pictureAdapter.isInitialized) {
            pictureAdapter = FilterPictureAdapter(requireActivity(), pictureList)
            with(binding.vpPicture) {
                adapter = pictureAdapter
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        Log.d(TAG, "initViewPager: position = $position")
                        selectedImgIndex = position
                        viewModel.setCurrentImgIndex(selectedImgIndex + 1)
                    }
                })
            }
        } else {
            // 선택된 인덱스로 변경되야함
            binding.vpPicture.currentItem = selectedImgIndex
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearData()
    }

    companion object {
        private const val TAG = "FilterFragment"
    }
}