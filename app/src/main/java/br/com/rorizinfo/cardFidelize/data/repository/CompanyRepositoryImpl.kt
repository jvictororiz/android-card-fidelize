package br.com.rorizinfo.cardFidelize.data.repository

import br.com.rorizinfo.cardFidelize.data.repository.contract.CompanyRepository
import br.com.rorizinfo.cardFidelize.data.repository.mapper.toCompanyRequest
import br.com.rorizinfo.cardFidelize.data.service.contract.CompanyService
import br.com.rorizinfo.cardFidelize.domain.model.Company

class CompanyRepositoryImpl(
    private val companyService: CompanyService
) : CompanyRepository {
    override suspend fun saveOrUpdateCompany(company: Company): Result<Void?> {
        return companyService.saveOrUpdateCompany(company.toCompanyRequest())
    }
}