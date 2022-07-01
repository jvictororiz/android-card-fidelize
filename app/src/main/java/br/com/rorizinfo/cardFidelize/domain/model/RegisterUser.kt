package br.com.rorizinfo.cardFidelize.domain.model

data class RegisterUser(
    val isVerified: Boolean = false,
    val email: String
)