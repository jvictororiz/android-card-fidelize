package br.com.rorizinfo.cardFidelize.data.repository

import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.data.repository.mapper.toRegisterUser
import br.com.rorizinfo.cardFidelize.data.repository.mapper.toUserRequest
import br.com.rorizinfo.cardFidelize.data.service.contract.UserLoginService
import br.com.rorizinfo.cardFidelize.domain.model.RegisterUser
import br.com.rorizinfo.cardFidelize.domain.model.User

class UserRepositoryImpl(
    private val loginService: UserLoginService,
) : UserRepository {
    override suspend fun saveUser(user: User): Result<RegisterUser> {
        return loginService.saveUser(user.toUserRequest()).map {
            it.toRegisterUser()
        }
    }
    
    override suspend fun sendEmailVerification(): Result<Void?> {
        return loginService.sendEmailVerification()
    }
    
    override suspend fun confirmPasswordReset(code: String, email: String): Result<Void?> {
        return loginService.confirmPasswordReset(code, email)
    }
    
    override suspend fun verifyConfirmationAccount(email: String, password: String): Boolean {
        return loginService.verifyConfirmationAccount(email, password)
    }
    
    override suspend fun doLogin(email: String, password: String): Result<RegisterUser> {
        return loginService.doLogin(email, password).map {
            it.toRegisterUser()
        }
    }
}