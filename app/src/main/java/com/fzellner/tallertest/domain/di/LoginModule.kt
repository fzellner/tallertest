package com.fzellner.tallertest.domain.di

import com.fzellner.tallertest.domain.repository.LoginRepository
import com.fzellner.tallertest.data.repository.LoginRepositoryImpl
import com.fzellner.tallertest.domain.usecase.LoginUseCase
import com.fzellner.tallertest.domain.usecase.LoginUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }

    @Provides
    fun providesLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCaseImpl(loginRepository)
    }
}