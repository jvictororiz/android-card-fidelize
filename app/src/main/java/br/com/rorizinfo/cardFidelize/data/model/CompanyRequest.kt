package br.com.rorizinfo.cardFidelize.data.model

data class CompanyRequest(
    val userRequest: RegisterUserRequest,
    val nameCompany: String,
    val cnpj: String
)
