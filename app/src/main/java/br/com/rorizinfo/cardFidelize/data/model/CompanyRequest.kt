package br.com.rorizinfo.cardFidelize.data.model

data class CompanyRequest(
    val userId: String,
    val userEmail: String,
    val nameCompany: String,
    val cnpj: String
)
