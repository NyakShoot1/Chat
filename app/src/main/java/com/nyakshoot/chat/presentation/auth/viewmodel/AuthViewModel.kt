package com.nyakshoot.chat.presentation.auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyakshoot.chat.domain.auth.AuthManager
import com.nyakshoot.chat.domain.auth.User
import com.nyakshoot.chat.domain.auth.isAuthorized
import com.nyakshoot.chat.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    private val _authState = MutableLiveData(
        if (authManager.isAuthorized()) {
            AuthState.Authorized
        } else {
            AuthState.Empty
        }
    )
    init {
        Log.d("test", authManager.getCurrentUser().toString())
    }

    val authState: LiveData<AuthState> get() = _authState

    fun logIn(email: String, password: String) {
        val validatedEmail = User.Email.createIfValid(email)
        val validatedPassword = User.Password.createIfValid(password)

        when {
            (validatedEmail == null) -> {
                _authState.value = AuthState.InputError.Email
            }

            (validatedPassword == null) -> {
                _authState.value = AuthState.InputError.Password
            }

            else -> {
                executeLogInRequest(validatedEmail, validatedPassword)
            }
        }
    }

    private fun executeLogInRequest(email: User.Email, password: User.Password) {
        _authState.value = AuthState.Authorizing
        viewModelScope.launch {
            val result = authManager.logIn(email, password)
            handleAuthResult(result)
        }
    }

    private fun handleAuthResult(result: Result<User.Id>) {
        _authState.value = when (result.status) {
            Result.Status.SUCCESS -> AuthState.Authorized
            Result.Status.ERROR -> AuthState.AuthError(result.message)
        }
    }

    fun resetAuthState() {
        if (_authState.value == AuthState.AuthError(logInError = null) ||
            _authState.value == AuthState.InputError.Email ||
            _authState.value == AuthState.InputError.Password
        ) {
            _authState.value = AuthState.Empty
        }
    }
}