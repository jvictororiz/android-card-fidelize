package br.com.rorizinfo.cardFidelize.data.repository.contract

import br.com.rorizinfo.cardFidelize.domain.model.Company

interface CompanyRepository {
    suspend fun saveOrUpdateCompany(company: Company): Result<Void?>
}