package com.cmc.purithm.feature.mypage.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
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
import com.cmc.purithm.feature.mypage.R
import com.cmc.purithm.feature.mypage.databinding.FragmentSettingBinding
import com.cmc.purithm.feature.mypage.viewmodel.SettingSideEffects
import com.cmc.purithm.feature.mypage.viewmodel.SettingState
import com.cmc.purithm.feature.mypage.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_setting

    private val viewModel: SettingViewModel by viewModels()
    private val navArgs by navArgs<SettingFragmentArgs>()
    private val userId by lazy { navArgs.id }
    private val username by lazy { navArgs.username }
    private val profile by lazy { navArgs.profile }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            is SettingState.SuccessDeleteAccount -> {}
                            is SettingState.Error -> {}
                            SettingState.Loading -> showLoadingDialog()
                            is SettingState.SuccessLogout -> {
                                (activity as NavigationAction).navigateOnBoarding()
                            }
                            SettingState.Initialize -> {}
                        }
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            SettingSideEffects.DeleteAccount -> {}
                            SettingSideEffects.Logout -> {
                                CommonDialogFragment.showDialog(
                                    content = "로그아웃 하시겠습니까?",
                                    positiveText = "로그아웃",
                                    negativeText = "취소",
                                    positiveClickEvent = {
                                        viewModel.logout()
                                    },
                                    negativeClickEvent = {
                                        CommonDialogFragment.dismissDialog()
                                    },
                                    fragmentManager = childFragmentManager
                                )
                            }
                            SettingSideEffects.NavigateToAccountInfo -> navigate(SettingFragmentDirections.actionSettingFragmentToMyAccountFragment())
                            SettingSideEffects.NavigateToEditProfile -> navigate(SettingFragmentDirections.actionSettingFragmentToEditProfileFragment(
                                username,
                                profile
                            ))
                            SettingSideEffects.NavigateToPersonalInfo -> startWeb(URL_PERSONAL_INFO)
                            SettingSideEffects.NavigateToTermsOfService -> startWeb(
                                URL_TERMS_OF_SERVICE
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
        binding.tvVersion.text = getAppVersion()
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.KR_BACK,
            title = "설정",
            backClickListener = {
                findNavController().popBackStack()
            }
        )
    }

    private fun startWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun getAppVersion(): String {
        val pInfo = requireContext().packageManager.getPackageInfo("com.cmc.purithm", 0)
        return pInfo.versionName
    }

    companion object {
        private const val URL_TERMS_OF_SERVICE =
            "https://palm-blizzard-691.notion.site/798f1bf6c507421584861961deb173d6?pvs=4"
        private const val URL_PERSONAL_INFO =
            "https://palm-blizzard-691.notion.site/d6a13c767dbd4ab88cb50b594e4ff6a6?pvs=4"
    }
}