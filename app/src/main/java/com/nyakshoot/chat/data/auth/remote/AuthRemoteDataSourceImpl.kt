package com.nyakshoot.chat.data.auth.remote

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.nyakshoot.chat.domain.auth.LogInError
import com.nyakshoot.chat.domain.auth.User
import com.nyakshoot.chat.utils.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor() : AuthRemoteDataSource {

    private val firebaseAuth: FirebaseAuth get() = FirebaseAuth.getInstance()

    override val user: User?
        get() = firebaseAuth.currentUser?.let { firebaseUser ->
            val userId = firebaseUser.uid.let(User::Id)
            val userEmail = firebaseUser.email.let(User.Email::createIfValid)

            if (userEmail != null) {
                User(userId, userEmail)
            } else {
                null
            }
        }

    override suspend fun createNewUser(
        email: User.Email,
        password: User.Password
    ): Result<User.Id> {
        return try {
            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(email.value, password.value).await()
            val userId = authResult.user?.uid ?: return Result.error(RegisterError.UNKNOWN.title)

            Result.success(User.Id(userId))
        } catch (e: FirebaseAuthException) {
            Result.error(RegisterError.USER_WITH_SUCH_CREDENTIALS_EXISTS.title)
        } catch (e: Exception) {
            Result.error(RegisterError.UNKNOWN.title)
        }
    }

    override suspend fun logIn(
        email: User.Email,
        password: User.Password
    ): Result<User.Id> {
        return try {
            val authResult =
                firebaseAuth.signInWithEmailAndPassword(email.value, password.value).await()
            val userId = authResult.user?.uid ?: return Result.error(LogInError.UNKNOWN.title)

            Result.success(User.Id(userId))
        } catch (e: FirebaseAuthException) {
            Result.error(LogInError.INVALID_USER_CREDENTIALS.title)
        } catch (e: Exception) {
            Result.error(LogInError.UNKNOWN.title)
        }
    }

    override suspend fun logOut() {
        firebaseAuth.signOut()
    }

}