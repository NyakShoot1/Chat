package com.nyakshoot.chat.data.auth

import com.nyakshoot.chat.domain.auth.User
import com.nyakshoot.chat.utils.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: User?

    fun observeCurrentUser(): Flow<User?>

    suspend fun register(
        email: User.Email,
        password: User.Password,
    ): Result<User.Id>

    suspend fun logIn(
        email: User.Email,
        password: User.Password
    ): Result<User.Id>

    suspend fun logOut()

}