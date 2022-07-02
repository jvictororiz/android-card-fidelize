package br.com.rorizinfo.cardFidelize.data.model

data class RegisterUserResponse(
    val idUser: String = "",
    val isVerified: Boolean = false,
    val email :String
)