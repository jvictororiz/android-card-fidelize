package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.CompanyRepository
import br.com.rorizinfo.cardFidelize.domain.model.Company

class SaveOrUpdateCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    
    suspend operator fun invoke(company: Company): Result<Void?> {
        return companyRepository.saveOrUpdateCompany(company)
    }
}