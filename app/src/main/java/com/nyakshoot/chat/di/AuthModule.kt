package com.nyakshoot.chat.di

import com.nyakshoot.chat.data.auth.AuthRepository
import com.nyakshoot.chat.data.auth.AuthRepositoryImpl
import com.nyakshoot.chat.data.auth.local.AuthLocalDataSource
import com.nyakshoot.chat.data.auth.local.AuthLocalDataSourceImpl
import com.nyakshoot.chat.data.auth.remote.AuthRemoteDataSource
import com.nyakshoot.chat.data.auth.remote.AuthRemoteDataSourceImpl
import com.nyakshoot.chat.domain.auth.AuthManager
import com.nyakshoot.chat.domain.auth.AuthManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun bindAuthLocalDataSource(
        impl: AuthLocalDataSourceImpl,
    ): AuthLocalDataSource

    @Binds
    fun bindAuthRemoteDataSource(
        impl: AuthRemoteDataSourceImpl,
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    fun bindAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    fun bindAuthManager(
        impl: AuthManagerImpl,
    ): AuthManager

}