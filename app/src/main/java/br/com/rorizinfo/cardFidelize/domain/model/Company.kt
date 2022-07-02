package br.com.rorizinfo.cardFidelize.domain.model

data class Company(
    val user: User,
    var cnpj: String = "",
    var nameCompany: String = ""
)
