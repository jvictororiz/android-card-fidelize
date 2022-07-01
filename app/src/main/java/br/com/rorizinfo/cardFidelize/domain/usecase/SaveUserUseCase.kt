package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.domain.model.RegisterUser
import br.com.rorizinfo.cardFidelize.domain.model.User

class SaveUserUseCase(private val repository: UserRepository) {
    
    suspend operator fun invoke(user: User): Result<RegisterUser> {
        return repository.saveUser(user)
    }
}