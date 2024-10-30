package com.nyakshoot.chat.presentation.chats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyakshoot.chat.domain.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {

    private val _isDone = MutableLiveData<Boolean>(false)
    val isDone: LiveData<Boolean> = _isDone

    fun logOut() = viewModelScope.launch {
        authManager.logOut()
        _isDone.value = true
    }
}