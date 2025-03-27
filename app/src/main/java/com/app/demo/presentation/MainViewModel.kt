package com.app.demo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.demo.feature.auth.shared.domain.usecase.GetLoggedInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLoggedInUserUseCase.invoke().fold(
                onSuccess = { _state.update { state -> state.copy(isLoggedIn = true) } },
                onFailure = { _state.update { state -> state.copy(isLoggedIn = false) } }
            )
        }
    }

    data class UiState(
        val isLoggedIn: Boolean? = null
    )
}

