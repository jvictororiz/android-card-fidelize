package br.com.rorizinfo.cardFidelize.data.model

data class RegisterUserResponse(
    val isVerified: Boolean = false,
    val email :String
)