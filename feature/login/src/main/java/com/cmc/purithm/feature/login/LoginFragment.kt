package com.cmc.purithm.feature.login

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.base.BaseFragment
import com.cmc.purithm.common.base.NavigationAction
import com.cmc.purithm.feature.login.databinding.FragmentLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_login

    private val kakaoCallback :(OAuthToken?, Throwable?) -> Unit = { token, error ->
        if(error != null){
            when(error.toString()){
                AuthErrorCause.AccessDenied.toString() -> showToast(getString(R.string.error_kakao_access_denied_msg))
                AuthErrorCause.InvalidClient.toString() -> showToast(getString(R.string.error_kakao_invalid_client_msg))
                AuthErrorCause.InvalidGrant.toString() -> showToast(getString(R.string.error_kakao_invalid_grant_msg))
                AuthErrorCause.InvalidRequest.toString() ->showToast(getString(R.string.error_kakao_invalid_request_msg))
                AuthErrorCause.Misconfigured.toString() -> showToast(getString(R.string.error_kakao_mis_configured_msg))
                AuthErrorCause.ServerError.toString() -> showToast(getString(R.string.error_kakao_server_error_msg))
                AuthErrorCause.Unauthorized.toString() -> showToast(getString(R.string.error_kakao_unauthorized_msg))
                else -> showToast(getString(R.string.error_kakao_default_msg))
            }
        } else if(token != null) {
            Log.d(TAG, "kakaoCallback : token = ${token.accessToken}")
            viewModel.joinKakao(token.accessToken)
        }
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.action.collect { action ->
                        when(action){
                            LoginAction.JoinKakao -> {
                                loginKakaoTalk()
                            }
                        }
                    }
                }

                launch {
                    viewModel.state.collect { state ->
                        when(state){
                            LoginState.Initial -> {}
                            LoginState.Success -> {
                                dismissLoadingDialog()
                            }
                            is LoginState.Error -> {
                                dismissLoadingDialog()
                                showSnackBar(state.message ?: getString(com.cmc.purithm.design.R.string.error_common))
                            }
                            LoginState.Loading -> {
                                showLoadingDialog()
                            }
                        }
                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->
                        when(sideEffect){
                            LoginSideEffects.NavigateToTerm -> (activity as NavigationAction).navigateTerm()
                            LoginSideEffects.NavigateToMain -> (activity as NavigationAction).navigateHome()
                        }
                    }
                }
            }
        }
    }

    private fun loginKakaoTalk() {
        Log.d(TAG, "loginKakaoTalk: start")
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())){
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

    override fun initView() {}

    companion object {
        private const val TAG = "LoginFragment"
    }
}