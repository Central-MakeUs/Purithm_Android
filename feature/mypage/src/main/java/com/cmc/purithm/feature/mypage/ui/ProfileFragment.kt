package com.cmc.purithm.feature.mypage.ui

import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.FragmentProfileBinding
import com.cmc.purithm.feature.mypage.viewmodel.ProfileSideEffects
import com.cmc.purithm.feature.mypage.viewmodel.ProfileState
import com.cmc.purithm.feature.mypage.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_profile

    private val viewModel by viewModels<ProfileViewModel>()

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is ProfileState.Error -> {
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

                            ProfileState.Initialize -> {
                                viewModel.getUser()
                            }

                            ProfileState.Loading -> {
                                showLoadingDialog()
                            }

                            is ProfileState.Success -> {
                                dismissLoadingDialog()
                                binding.data = state.data
                                binding.viewStamp.setStampCount(state.data.stampCnt)
                            }
                        }
                    }
                }
                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            ProfileSideEffects.NavigateFilterHistory -> TODO()
                            ProfileSideEffects.NavigateLike -> TODO()
                            is ProfileSideEffects.NavigateProfileSetting -> TODO()
                            ProfileSideEffects.NavigateReviewHistory -> TODO()
                            is ProfileSideEffects.NavigateSetting -> {
                                navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment(
                                    sideEffect.id,
                                    sideEffect.username,
                                    sideEffect.profile
                                ))
                            }
                            ProfileSideEffects.NavigateStamp -> TODO()
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
        setBackButtonEvent()
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.ENG_SETTING,
            settingClickListener = {
                viewModel.clickSetting(
                    binding.data?.id!!,
                    binding.data?.username!!,
                    binding.data?.profile ?: ""
                )
            }
        )
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
}