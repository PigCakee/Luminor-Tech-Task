package com.app.demo.feature.auth.wiring

import com.app.demo.feature.auth.impl.data.api.AuthWebService
import com.app.demo.feature.auth.impl.data.api.MockAuthWebService
import com.app.demo.feature.auth.impl.data.repository.AuthRepositoryImpl
import com.app.demo.feature.auth.impl.data.repository.UserRepositoryImpl
import com.app.demo.feature.auth.impl.domain.usecase.GetLoggedInUserUseCaseImpl
import com.app.demo.feature.auth.impl.domain.usecase.LoginUseCaseImpl
import com.app.demo.feature.auth.impl.domain.usecase.RegisterUseCaseImpl
import com.app.demo.feature.auth.impl.domain.usecase.SaveLoggedInUserUseCaseImpl
import com.app.demo.feature.auth.shared.domain.repository.AuthRepository
import com.app.demo.feature.auth.shared.domain.repository.UserRepository
import com.app.demo.feature.auth.shared.domain.usecase.GetLoggedInUserUseCase
import com.app.demo.feature.auth.shared.domain.usecase.LoginUseCase
import com.app.demo.feature.auth.shared.domain.usecase.RegisterUseCase
import com.app.demo.feature.auth.shared.domain.usecase.SaveLoggedInUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindLogInUseCase(impl: LoginUseCaseImpl): LoginUseCase

    @Binds
    fun bindGetLoggedInUseCase(impl: GetLoggedInUserUseCaseImpl): GetLoggedInUserUseCase

    @Binds
    fun bindSaveLoggedInUserUseCase(impl: SaveLoggedInUserUseCaseImpl): SaveLoggedInUserUseCase

    @Binds
    fun bindRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

    @Binds
    fun bindAuthWebService(impl: MockAuthWebService): AuthWebService
}