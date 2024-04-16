package com.fzellner.tallertest.presentation.state

sealed class LoginViewState {
    data class Loading(val isLoading: Boolean = false): LoginViewState()
    object Success: LoginViewState()
    object Error: LoginViewState()
}