package br.com.rorizinfo.cardFidelize.data.repository.mapper

import br.com.rorizinfo.cardFidelize.data.model.RegisterUserRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserResponse
import br.com.rorizinfo.cardFidelize.domain.model.RegisterUser
import br.com.rorizinfo.cardFidelize.domain.model.User

fun RegisterUserResponse.toRegisterUser() = RegisterUser(
    id = idUser,
    isVerified = isVerified,
    email = email
)

fun User.toUserRequest() = RegisterUserRequest(
    id = id,
    email = email,
    password = password
)