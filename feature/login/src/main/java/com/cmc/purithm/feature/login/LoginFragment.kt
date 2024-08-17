package com.cmc.purithm.feature.login

import android.util.Log
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.common.dialog.CommonDialogFragment
import com.cmc.purithm.design.util.Util
import com.cmc.purithm.design.util.Util.showPurithmSnackBar
import com.cmc.purithm.feature.login.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.KakaoJson
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_login

    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            if (error is AuthError && error.statusCode == 302) {
                loginKakaoAccount()
            } else {
                showSnackBar(
                    view = requireView(), message = error.message ?: getString(R.string.error_kakao_default_msg)
                )
            }
        } else if (token != null) {
            Log.d(TAG, "kakaoCallback : token = ${token.accessToken}")
            viewModel.joinKakao(token.accessToken)
        }
    }

    private fun loginKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.action.collect { action ->
                        when (action) {
                            LoginAction.JoinKakao -> {
                                loginKakaoTalk()
                            }
                        }
                    }
                }

                launch {
                    viewModel.state.collect { state ->
                        when (state) {
                            LoginState.Initial -> {}
                            LoginState.Success -> {
                                dismissLoadingDialog()
                            }

                            is LoginState.Error -> {
                                dismissLoadingDialog()
                                showSnackBar(
                                    view = requireView(),
                                    message = state.message
                                        ?: getString(com.cmc.purithm.design.R.string.error_common)
                                )
                            }

                            LoginState.Loading -> {
                                showLoadingDialog()
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when (sideEffect) {
                            LoginSideEffects.NavigateToTerm -> (activity as NavigationAction).navigateTermOfService()
                            LoginSideEffects.NavigateToMain -> (activity as NavigationAction).navigateHome()
                        }
                    }
                }
            }
        }
    }


    private fun loginKakaoTalk() {
        Log.d(TAG, "loginKakaoTalk: start")
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            Log.d(TAG, "loginKakaoTalk: kakaotalk is installed")
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoCallback)
        } else {
            Log.d(TAG, "loginKakaoTalk: kakaotalk is not installed")
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
        }
        Log.d(TAG, "loginKakaoTalk: start")
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initView() {
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

    companion object {
        private const val TAG = "LoginFragment"
    }
}