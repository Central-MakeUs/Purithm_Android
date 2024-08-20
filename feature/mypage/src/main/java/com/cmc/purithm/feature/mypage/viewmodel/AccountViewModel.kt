package com.cmc.purithm.feature.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.domain.usecase.member.GetAccountUseCase
import com.cmc.purithm.feature.mypage.model.AccountUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<AccountState>(AccountState.Loading)
    val state = _state.asStateFlow()

    init {
        getAccount()
    }

    private fun getAccount() {
        viewModelScope.launch {
            _state.value = AccountState.Loading
            runCatching {
                getAccountUseCase()
            }.onSuccess {
                _state.value = AccountState.Success(AccountUiModel.toUiModel(it))
            }.onFailure {
                _state.value = AccountState.Error(it.message ?: "알 수 없는 에러")
            }
        }
    }
}

sealed interface AccountState {
    data object Initialize : AccountState
    data object Loading : AccountState
    data class Success(val data: AccountUiModel) : AccountState
    data class Error(val message: String) : AccountState
}