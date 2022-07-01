package br.com.rorizinfo.cardFidelize.data.model

data class RegisterUserRequest(
    var id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
)