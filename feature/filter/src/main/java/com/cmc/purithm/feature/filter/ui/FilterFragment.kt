package com.cmc.purithm.feature.filter.ui

import android.util.Log
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.domain.entity.filter.FilterImg
import com.cmc.purithm.feature.filter.R
import com.cmc.purithm.feature.filter.adapter.FilterPictureAdapter
import com.cmc.purithm.feature.filter.databinding.FragmentFilterBinding
import com.cmc.purithm.feature.filter.util.BlurUtil.blurBitmap
import com.cmc.purithm.feature.filter.viewmodel.FilterSideEffects
import com.cmc.purithm.feature.filter.viewmodel.FilterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_filter
    private val viewModel: FilterViewModel by hiltNavGraphViewModels(R.id.nav_filter)
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
                        if (state.data != null) {
                            initAppBar(state.data.name, state.data.liked, state.data.likes)
                            initViewPager(state.data.pictures)
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
                        if(state.isFirst){
                            binding.layoutMain.post {
                                val blurBitmap = requireContext().blurBitmap(binding.layoutMain)
                                binding.viewGuide.imgBlurBackground.setImageBitmap(blurBitmap)
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        Log.d(TAG, "initObserving: sideEffect on")
                        when (sideEffect) {
                            is FilterSideEffects.NavigateFilterIntroduction -> navigate(
                                FilterFragmentDirections.actionFilterFragmentToFilterIntroductionFragment(
                                    filterId
                                )
                            )

                            is FilterSideEffects.NavigateFilterReview -> {
                                navigate(
                                    FilterFragmentDirections.actionFilterFragmentToFilterReviewFragment(
                                        filterId,
                                        viewModel.state.value.data?.pictures?.get(0)?.picture!!,
                                        viewModel.state.value.data?.name ?: ""
                                    )
                                )
                            }

                            is FilterSideEffects.NavigateFilterLoading -> {
                                navigate(
                                    FilterFragmentDirections.actionFilterFragmentToFilterLoadingFragment(
                                        filterId
                                    )
                                )
                            }

                            FilterSideEffects.ShowFilterLikeSnackBar -> {
                                showSnackBar(
                                    view = requireView(),
                                    message = "찜 목록에 담겼어요",
                                    actionString = "찜 목록 보기",
                                    action = {
                                        Log.d(TAG, "initObserving: snackbar on!")
                                        // TODO : Navigate 추가해야함
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
        binding.vm = viewModel
    }

    override fun initView() {
        viewModel.getFilterDetail(filterId)
        initGuide()
    }

    private fun initGuide() {
        binding.viewGuide.btnGuideConfirm.setOnClickListener {
            viewModel.setFilterFirstRun(false)
        }
    }

    private fun initAppBar(title: String, liked: Boolean, likes: Int) {
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.ENG_LIKE,
            title = title,
            likeState = liked,
            likeCnt = likes,
            backClickListener = {
                findNavController().popBackStack()
            },
            likeClickListener = {
                if (viewModel.state.value.data?.liked == true) {
                    viewModel.deleteFilterLike(filterId)
                } else {
                    viewModel.requestFilterLike(filterId)
                }
            }
        )
    }

    private fun initViewPager(pictureList: List<FilterImg>) {
        Log.d(TAG, "initViewPager: start")
        if (binding.vpPicture.adapter == null) {
            Log.d(TAG, "initViewPager: init start")
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
            Log.d(TAG, "initViewPager: already init")
            binding.vpPicture.currentItem = viewModel.state.value.selectedIndex - 1
        }
    }

    companion object {
        private const val TAG = "FilterFragment"
    }
}