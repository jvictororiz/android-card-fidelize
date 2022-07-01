package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository

class ConfirmPasswordResetUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(code: String, email: String): Result<Void?> {
        return repository.confirmPasswordReset(code, email)
    }
}