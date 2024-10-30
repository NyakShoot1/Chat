package com.nyakshoot.chat.data.auth.local

import com.nyakshoot.chat.domain.auth.User
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {
    var currentUser: User?

    fun observeCurrentUser(): Flow<User?>
}

fun AuthLocalDataSource.logOut() {
    this.currentUser = null
}