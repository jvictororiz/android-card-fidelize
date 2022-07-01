package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.domain.model.RegisterUser

class VerifyValidationAccountUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.verifyConfirmationAccount(email, password)
    }
}