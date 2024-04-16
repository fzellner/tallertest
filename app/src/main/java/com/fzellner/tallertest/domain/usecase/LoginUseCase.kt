package com.fzellner.tallertest.domain.usecase

import kotlinx.coroutines.flow.Flow

interface LoginUseCase  {
    suspend operator fun invoke(
        userEmail: String,
        userPassword: String
    ) : Flow<Boolean>
}