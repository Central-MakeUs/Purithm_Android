package com.cmc.purithm.feature.term.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.addCallback
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.component.appbar.PurithmAppbar
import com.cmc.purithm.feature.term.R
import com.cmc.purithm.feature.term.databinding.FragmentTermOfServiceBinding

class TermOfServiceFragment : BaseFragment<FragmentTermOfServiceBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_term_of_service

    override fun initObserving() {}

    override fun initBinding() {}

    override fun initView() {
        with(binding) {
            btnTermOfService.setOnClickListener {
                moveTermOfService()
            }
            btnTermOfServiceAgreement.setOnCheckedChangeListener { _, isChecked ->
                btnJoinComplete.isEnabled = isChecked
            }
            btnJoinComplete.setOnClickListener {
                navigate(TermOfServiceFragmentDirections.actionTermOfServiceFragmentToJoinCompleteFragment())
            }
        }
        setBackButtonEvent()
        initAppbar()
    }

    private fun setBackButtonEvent(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            CommonDialogFragment.showDialog(
                title = getString(R.string.content_term_of_service_cancel_description),
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

    private fun initAppbar(){
        binding.viewAppbar.setAppBar(
            type = PurithmAppbar.PurithmAppbarType.KR_BACK,
            title = getString(com.cmc.purithm.design.R.string.title_term_of_service),
            backClickListener = {
                CommonDialogFragment.showDialog(
                    title = getString(com.cmc.purithm.feature.term.R.string.content_term_of_service_cancel_description),
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
        // FIXME : 노션 공유로 변경 후 재설정
        private const val TERM_OF_SERVICE_URL =
            "https://www.notion.so/94da255dcb0f4ba6b2e5c8f956c8c2ac"
    }
}