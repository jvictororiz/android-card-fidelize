package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository

class SendEmailVerificationUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): Result<Void?> {
        return repository.sendEmailVerification()
    }
}