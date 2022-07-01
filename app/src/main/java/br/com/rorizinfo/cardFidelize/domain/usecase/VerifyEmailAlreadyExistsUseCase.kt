package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository

class VerifyEmailAlreadyExistsUseCase(
    private val repository: UserRepository
) {
    
    suspend operator fun invoke(email: String): Boolean {
        return repository.verifyEmailAlreadyExists(email)
    }
}