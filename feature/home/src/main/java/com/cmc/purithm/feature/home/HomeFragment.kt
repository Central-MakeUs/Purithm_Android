package com.cmc.purithm.feature.home

import android.util.Log
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.home.adpater.HomeFilterAdapter
import com.cmc.purithm.feature.home.dialog.HomeFilterLockBottomDialog
import com.cmc.purithm.feature.home.dialog.HomeItemFilterDialogFragment
import com.cmc.purithm.feature.home.model.HomeFilterUiModel
import com.cmc.purtihm.feature.home.R
import com.cmc.purtihm.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()

    private var lastCheckedId = -1
    private val homeItemFilterDialogFragment by lazy { HomeItemFilterDialogFragment() }
    private val homeFilterLockBottomDialog by lazy { HomeFilterLockBottomDialog() }
    private val homeFilterAdapter by lazy { HomeFilterAdapter(viewModel) }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        Log.d(TAG, "initObserving: state update")
                        if (state.loading) {
                            Log.d(TAG, "initObserving: loading true")
                            showLoadingDialog()
                        } else {
                            Log.d(TAG, "initObserving: loading false")
                            dismissLoadingDialog()
                        }
                        if (state.error != null) {
                            Log.e(TAG, "initObserving: error = ${state.error.message}")
                            CommonDialogFragment.showDialog(
                                content = getString(com.cmc.purithm.design.R.string.error_common),
                                positiveText = getString(com.cmc.purithm.design.R.string.content_confirm),
                                positiveClickEvent = {
                                    requireActivity().finish()
                                },
                                fragmentManager = childFragmentManager
                            )
                        }
                        homeFilterAdapter.submitData(state.filterList)
                    }
                }
                launch {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is HomeSideEffect.NavigateToFilter -> (activity as NavigationAction).navigateFilterItem(
                                sideEffect.id
                            )

                            HomeSideEffect.ShowFilterLockBottomSheet -> homeFilterLockBottomDialog.show(
                                childFragmentManager,
                                null
                            )

                            HomeSideEffect.ShowFilterSortedBottomSheet -> homeItemFilterDialogFragment.show(
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
        initRadioGroup()
        viewModel.setPageAdapterLoadStateListener(homeFilterAdapter)
        with(binding) {
            listFilter.adapter = homeFilterAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = getString(R.string.title_appbar)
            )
        }
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
                            viewModel.clickFilterTag("")
                        } else {
                            view.isChecked = true
                            lastCheckedId = view.id
                            viewModel.clickFilterTag(convertTag(view.text.toString()))
                        }
                    }
                }
            }
        }
    }

    private fun convertTag(tag: String) = when (tag) {
        "봄" -> "spring"
        "여름" -> "summer"
        "가을" -> "fall"
        "겨울" -> "winter"
        "역광에서" -> "backlight"
        "고양이" -> "cat"
        else -> tag
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}