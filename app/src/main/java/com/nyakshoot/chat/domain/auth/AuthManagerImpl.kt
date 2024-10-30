package com.nyakshoot.chat.domain.auth

import com.nyakshoot.chat.data.auth.AuthRepository
import com.nyakshoot.chat.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthManagerImpl @Inject constructor(
    private val authRepository: AuthRepository
) : AuthManager {
    override fun getCurrentUser(): User? = authRepository.currentUser


    override fun observeCurrentUser(): Flow<User?> = authRepository.observeCurrentUser()

    override suspend fun register(email: User.Email, password: User.Password): Result<User.Id> =
        authRepository.register(email, password)


    override suspend fun logIn(email: User.Email, password: User.Password): Result<User.Id> =
        authRepository.logIn(email, password)

    override suspend fun logOut() {
        authRepository.logOut()
    }
}