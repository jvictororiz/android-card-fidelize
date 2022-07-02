package br.com.rorizinfo.cardFidelize.domain.model

data class Company(
    val user: User,
    var cnpj: String = "",
    val nameCompany: String = ""
)
