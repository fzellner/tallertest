package com.fzellner.tallertest.domain.usecase

import com.fzellner.tallertest.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(private val loginRepository: LoginRepository) :
    LoginUseCase {
    override suspend fun invoke(userEmail: String, userPassword: String): Flow<Boolean> {
            return loginRepository.login(password = userPassword, userEmail = userEmail)
    }
}