package br.com.rorizinfo.cardFidelize.domain.model

data class RegisterUser(
    val id: String = "",
    val isVerified: Boolean = false,
    val email: String
)