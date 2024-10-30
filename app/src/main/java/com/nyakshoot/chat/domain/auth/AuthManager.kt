package com.nyakshoot.chat.domain.auth

import com.nyakshoot.chat.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AuthManager {

    fun getCurrentUser(): User?

    fun observeCurrentUser(): Flow<User?>

    suspend fun register(
        email: User.Email,
        password: User.Password
    ): Result<User.Id>

    suspend fun logIn(
        email: User.Email,
        password: User.Password
    ): Result<User.Id>

    suspend fun logOut()

}

fun AuthManager.isAuthorized(): Boolean = this.getCurrentUser().isAuthorized

fun AuthManager.observeIsAuthorized(): Flow<Boolean> =
    this.observeCurrentUser()
        .map { currentUser -> currentUser.isAuthorized }

private val User?.isAuthorized: Boolean
    get() = (this != null)