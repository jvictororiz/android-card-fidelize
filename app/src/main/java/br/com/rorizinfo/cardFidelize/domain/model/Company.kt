package br.com.rorizinfo.cardFidelize.domain.model

data class Company(
    val user: User,
    val cnpj: String = "",
    val nameCompany: String = ""
)
