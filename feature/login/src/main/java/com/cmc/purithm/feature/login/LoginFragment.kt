package com.cmc.purithm.feature.login

import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cmc.purithm.common.ui.base.BaseFragment
import com.cmc.purithm.feature.login.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
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
            // TODO : ViewModel에서 UseCase 실행
        }
    }

    override fun initObserving() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.action.collect { action ->
                        when(action){
                            is LoginAction.JoinKakao -> {
                                loginKakaoTalk()
                            }
                        }
                    }
                }

                launch {
                    viewModel.state.collect { state ->

                    }
                }

                launch {
                    viewModel.sideEffects.collect { sideEffect ->

                    }
                }
            }
        }
    }

    private fun loginKakaoTalk() {
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())){
            UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = kakaoCallback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = kakaoCallback)
        }
    }

    override fun initBinding() {
        binding.vm = viewModel
    }

    override fun initView() {}
}