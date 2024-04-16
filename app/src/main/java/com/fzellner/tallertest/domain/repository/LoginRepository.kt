package com.fzellner.tallertest.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(userEmail: String, password: String): Flow<Boolean>
}