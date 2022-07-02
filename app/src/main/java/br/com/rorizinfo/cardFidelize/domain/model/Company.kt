package br.com.rorizinfo.cardFidelize.domain.model

data class Company(
    val userId: String,
    val userEmail: String,
    var cnpj: String = "",
    var nameCompany: String = ""
)
