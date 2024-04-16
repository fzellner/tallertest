package com.fzellner.tallertest.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fzellner.tallertest.domain.usecase.LoginUseCase
import com.fzellner.tallertest.presentation.state.LoginViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {

    private val _loginViewState = MutableStateFlow<LoginViewState>(LoginViewState.Loading(false))
    val loginViewState: StateFlow<LoginViewState>
        get() = _loginViewState

    fun doLogin(
        userEmail: String,
        userPassword: String
    ) {
        viewModelScope.launch {
            loginUseCase.invoke(userEmail, userPassword)
                .onStart {
                    _loginViewState.value = LoginViewState.Loading(true)
                }
                .catch {
                    _loginViewState.value = LoginViewState.Error
                }
                .collect { success ->
                    _loginViewState.value = if(success) LoginViewState.Success else LoginViewState.Error
                }
        }
    }
}