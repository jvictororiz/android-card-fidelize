package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.domain.model.RegisterUser

class DoLoginUserCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String, password: String): Result<RegisterUser> {
        return repository.doLogin(email, password)
    }
}