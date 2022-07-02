package br.com.rorizinfo.cardFidelize.data.service.contract

import br.com.rorizinfo.cardFidelize.data.model.ClientRequest
import br.com.rorizinfo.cardFidelize.data.model.CompanyRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserResponse

interface CompanyService {
    suspend fun saveOrUpdateCompany(companyRequest: CompanyRequest): Result<Void?>
}