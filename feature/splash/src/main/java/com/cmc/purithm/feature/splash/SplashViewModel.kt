package com.cmc.purithm.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmc.purithm.common.ui.base.NavigationAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO 토큰 검증 로직 구현 필요
class SplashViewModel @Inject constructor(

) : ViewModel() {
    private val _state : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val state : StateFlow<Boolean> get() =  _state.asStateFlow()

    fun test() {
        viewModelScope.launch {
            _state.emit(true)
        }
    }

}