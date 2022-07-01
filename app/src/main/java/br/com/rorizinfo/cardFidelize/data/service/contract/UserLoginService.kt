package br.com.rorizinfo.cardFidelize.data.service.contract

import br.com.rorizinfo.cardFidelize.data.model.RegisterUserRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserResponse

interface UserLoginService {
    suspend fun saveUser(user: RegisterUserRequest): Result<RegisterUserResponse>
    suspend fun sendEmailVerification(): Result<Void?>
    suspend fun confirmPasswordReset(code: String, email: String): Result<Void?>
    suspend fun doLogin(email: String, password: String): Result<RegisterUserResponse>
    suspend fun verifyConfirmationAccount(email: String, password: String): Boolean
}