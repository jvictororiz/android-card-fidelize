package br.com.rorizinfo.cardFidelize.data.repository.mapper

import br.com.rorizinfo.cardFidelize.data.model.CompanyRequest
import br.com.rorizinfo.cardFidelize.domain.model.Company


fun Company.toCompanyRequest() = CompanyRequest(
    userRequest = user.toUserRequest(),
    nameCompany = nameCompany,
    cnpj = cnpj
)