package com.cmc.purithm.feature.home

import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.home.adpater.HomeFilterAdapter
import com.cmc.purithm.feature.home.databinding.FragmentHomeBinding
import com.cmc.purithm.feature.home.dialog.HomeFilterLockBottomDialog
import com.cmc.purithm.feature.home.dialog.HomeItemFilterDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModels()

    private val homeFilterKeywordArray by lazy { resources.getStringArray(R.array.category_filter_keyword) }
    private val homeItemFilterDialogFragment by lazy { HomeItemFilterDialogFragment() }
    private val homeFilterLockBottomDialog by lazy { HomeFilterLockBottomDialog() }
    private val homeFilterAdapter by lazy { HomeFilterAdapter(viewModel) }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collectLatest { state ->
                        if (state.loading) {
                            binding.viewLoading.visibility = View.VISIBLE
                        } else {
                            binding.viewLoading.visibility = View.GONE
                        }
                        if(state.isEmpty){
                            binding.layoutEmptyView.visibility = View.VISIBLE
                        } else {
                            binding.layoutEmptyView.visibility = View.GONE
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
                                sideEffect.id, false
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
        setBackButtonEvent()
        viewModel.setPageAdapterLoadStateListener(homeFilterAdapter)
        with(binding) {
            listFilter.adapter = homeFilterAdapter
            viewAppbar.setAppBar(
                type = PurithmAppbar.PurithmAppbarType.ENG_TEXT,
                title = getString(R.string.title_appbar)
            )
        }
    }

    private fun setBackButtonEvent(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            CommonDialogFragment.showDialog(
                content = "앱을 종료하시겠습니까?",
                negativeText = getString(com.cmc.purithm.design.R.string.content_cancel),
                negativeClickEvent = {
                    CommonDialogFragment.dismissDialog()
                },
                positiveText = "종료",
                positiveClickEvent = {
                    CommonDialogFragment.dismissDialog()
                    requireActivity().finish()
                },
                fragmentManager = childFragmentManager
            )
        }
    }

    private fun initRadioGroup() {
        binding.groupFilterKeyword.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.clickFilterTag(homeFilterKeywordArray[group.indexOfChild(radioButton)])
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}