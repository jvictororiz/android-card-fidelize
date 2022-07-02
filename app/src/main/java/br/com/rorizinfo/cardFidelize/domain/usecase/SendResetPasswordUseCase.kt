package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository

class SendResetPasswordUseCase(
    private val repository: UserRepository
) {
    
    suspend operator fun invoke(email: String): Result<Void?> {
        return repository.sendPasswordResetEmail(email)
    }
}