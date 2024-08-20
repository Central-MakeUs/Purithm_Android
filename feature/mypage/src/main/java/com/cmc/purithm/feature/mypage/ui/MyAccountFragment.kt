package com.cmc.purithm.feature.mypage.ui

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.FragmentAccountBinding
import com.cmc.purithm.feature.mypage.viewmodel.AccountState
import com.cmc.purithm.feature.mypage.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyAccountFragment : BaseFragment<FragmentAccountBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_account

    private val viewModel by viewModels<AccountViewModel>()

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        AccountState.Initialize -> {}
                        is AccountState.Error -> {
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

                        AccountState.Loading -> showLoadingDialog()
                        is AccountState.Success -> {
                            dismissLoadingDialog()
                            binding.data = state.data
                        }
                    }
                }
            }
        }
    }

    override fun initBinding() {}

    override fun initView() {
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.KR_BACK,
            title = "계정 정보",
            backClickListener = {
                findNavController().popBackStack()
            }
        )
    }
}