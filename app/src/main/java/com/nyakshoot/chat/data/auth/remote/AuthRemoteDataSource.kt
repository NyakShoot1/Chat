package com.nyakshoot.chat.data.auth.remote

import com.nyakshoot.chat.domain.auth.User
import com.nyakshoot.chat.utils.Result

interface AuthRemoteDataSource {

    val user: User?

    suspend fun createNewUser(
        email: User.Email,
        password: User.Password,
    ): Result<User.Id>

    suspend fun logIn(
        email: User.Email,
        password: User.Password,
    ): Result<User.Id>

    suspend fun logOut()

}