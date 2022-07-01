package br.com.rorizinfo.cardFidelize.data.repository.contract

import br.com.rorizinfo.cardFidelize.domain.model.RegisterUser
import br.com.rorizinfo.cardFidelize.domain.model.User

interface UserRepository {
    suspend fun saveUser(user: User): Result<RegisterUser>
    suspend fun sendEmailVerification(): Result<Void?>
    suspend fun confirmPasswordReset(code: String, email: String): Result<Void?>
    suspend fun doLogin(email: String, password: String): Result<RegisterUser>
    suspend fun verifyConfirmationAccount(email: String, password: String): Boolean
    suspend fun verifyEmailAlreadyExists(email: String): Boolean
}