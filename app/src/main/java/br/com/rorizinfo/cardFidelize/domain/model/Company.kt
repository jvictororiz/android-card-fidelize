package br.com.rorizinfo.cardFidelize.domain.model

data class Company(
    val cnpj: String = "",
    val nameCompany: String = ""
) : User()
