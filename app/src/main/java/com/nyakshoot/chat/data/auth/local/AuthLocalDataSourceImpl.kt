package com.nyakshoot.chat.data.auth.local

import com.nyakshoot.chat.domain.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor() : AuthLocalDataSource {

    private val userFlow: MutableStateFlow<User?> = MutableStateFlow(null)

    override var currentUser: User?
        get() = userFlow.value
        set(newValue) {
            userFlow.value = newValue
        }

    override fun observeCurrentUser(): Flow<User?> = userFlow
}