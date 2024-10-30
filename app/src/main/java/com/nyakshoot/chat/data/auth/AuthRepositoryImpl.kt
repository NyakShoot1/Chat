package com.nyakshoot.chat.data.auth

import com.nyakshoot.chat.data.auth.local.AuthLocalDataSource
import com.nyakshoot.chat.data.auth.local.logOut
import com.nyakshoot.chat.data.auth.remote.AuthRemoteDataSource
import com.nyakshoot.chat.domain.auth.User
import com.nyakshoot.chat.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    init {
        authLocalDataSource.currentUser = authRemoteDataSource.user
    }

    override val currentUser: User?
        get() = authLocalDataSource.currentUser

    override fun observeCurrentUser(): Flow<User?> = authLocalDataSource.observeCurrentUser()

    override suspend fun register(email: User.Email, password: User.Password): Result<User.Id> {
        return authRemoteDataSource.createNewUser(email, password)
    }

    override suspend fun logOut() {
        authRemoteDataSource.logOut()
        authLocalDataSource.logOut()
    }

    override suspend fun logIn(email: User.Email, password: User.Password): Result<User.Id> {
        return authRemoteDataSource.logIn(email, password)
    }

}