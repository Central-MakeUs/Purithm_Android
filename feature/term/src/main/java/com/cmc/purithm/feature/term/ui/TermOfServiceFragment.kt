package com.cmc.purithm.feature.term.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.term.R
import com.cmc.purithm.feature.term.databinding.FragmentTermOfServiceBinding
import com.cmc.purithm.feature.term.viewmodel.TermOfServiceViewModel
import com.cmc.purithm.feature.term.viewmodel.TermsOfServiceAction
import com.cmc.purithm.feature.term.viewmodel.TermsOfServiceSideEffects
import com.cmc.purithm.feature.term.viewmodel.TermsOfServiceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TermOfServiceFragment : BaseFragment<FragmentTermOfServiceBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_term_of_service

    private val viewModel: TermOfServiceViewModel by viewModels()

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.action.collect {
                        when (it) {
                            TermsOfServiceAction.RequestAgreeToTermsOfService -> {
                                viewModel.requestAgreeToTermsOfService()
                            }
                        }
                    }
                }
                launch {
                    viewModel.state.collect {
                        when (it) {
                            is TermsOfServiceState.Error -> {
                                dismissLoadingDialog()
                                CommonDialogFragment.showDialog(
                                    content = it.message,
                                    positiveClickEvent = {
                                        CommonDialogFragment.dismissDialog()
                                    },
                                    fragmentManager = childFragmentManager
                                )
                            }

                            TermsOfServiceState.Loading -> showLoadingDialog()
                            TermsOfServiceState.Success -> dismissLoadingDialog()
                            else -> {}
                        }
                    }
                }
                launch {
                    viewModel.sideEffect.collect {
                        when (it) {
                            TermsOfServiceSideEffects.NavigateJoinComplete -> navigate(
                                TermOfServiceFragmentDirections.actionTermOfServiceFragmentToJoinCompleteFragment()
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
        with(binding) {
            btnTermOfService.setOnClickListener {
                moveTermOfService()
            }
            btnTermOfServiceAgreement.setOnCheckedChangeListener { _, isChecked ->
                btnJoinComplete.isEnabled = isChecked
            }
        }
        setBackButtonEvent()
        initAppbar()
    }

    private fun setBackButtonEvent() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            CommonDialogFragment.showDialog(
                content = getString(R.string.content_term_of_service_cancel_description),
                negativeText = getString(com.cmc.purithm.design.R.string.content_cancel),
                negativeClickEvent = {
                    CommonDialogFragment.dismissDialog()
                },
                positiveText = getString(R.string.content_term_of_service_cancel),
                positiveClickEvent = {
                    CommonDialogFragment.dismissDialog()
                    (activity as NavigationAction).navigateLogin()
                },
                fragmentManager = childFragmentManager
            )
        }
    }

    /**
     * 이용약관 웹 브라우저로 이동
     * */
    private fun moveTermOfService() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TERM_OF_SERVICE_URL)))
    }

    private fun initAppbar() {
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.KR_BACK,
            title = getString(com.cmc.purithm.design.R.string.title_term_of_service),
            backClickListener = {
                CommonDialogFragment.showDialog(
                    content = getString(com.cmc.purithm.feature.term.R.string.content_term_of_service_cancel_description),
                    negativeText = getString(com.cmc.purithm.design.R.string.content_cancel),
                    negativeClickEvent = {
                        CommonDialogFragment.dismissDialog()
                    },
                    positiveText = getString(com.cmc.purithm.feature.term.R.string.content_term_of_service_cancel),
                    positiveClickEvent = {
                        CommonDialogFragment.dismissDialog()
                        (activity as NavigationAction).navigateLogin()
                    },
                    fragmentManager = childFragmentManager
                )
            }
        )
    }

    companion object {
        private const val TERM_OF_SERVICE_URL = "https://palm-blizzard-691.notion.site/798f1bf6c507421584861961deb173d6?pvs=4"
    }
}