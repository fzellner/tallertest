package com.fzellner.tallertest.data.repository

import com.fzellner.tallertest.domain.repository.LoginRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(userEmail: String, password: String): Flow<Boolean> {
        val successEmail = "email@test.com"
        return flow {
            delay(3000)
            emit(userEmail == successEmail && password == "12345")
        }
    }

}