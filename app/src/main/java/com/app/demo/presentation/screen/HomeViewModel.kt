package com.app.demo.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.demo.feature.auth.shared.domain.usecase.GetLoggedInUserUseCase
import com.app.demo.navigation.shared.AuthListener
import com.app.demo.navigation.shared.navigation.NavigationManager
import com.app.demo.navigation.shared.navigation.navigateToSignInUp
import com.app.demo.presentation.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authListener: AuthListener,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLoggedInUserUseCase.invoke().fold(
                onSuccess = { user ->
                    _state.update { state ->
                        state.copy(user = UserUiModel(email = user.email))
                    }
                },
                onFailure = { signOut() }
            )
        }
    }

    fun acceptIntent(intent: UiIntent) {
        when (intent) {
            is UiIntent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authListener.signOut()
        }
    }

    sealed interface UiIntent {
        data object SignOut : UiIntent
    }

    data class UiState(
        val user: UserUiModel? = null
    )
}