package br.com.rorizinfo.cardFidelize.domain.model

data class Client(
    val user: User,
    val name: String = ""
)