package br.com.rorizinfo.cardFidelize.domain.usecase

import br.com.rorizinfo.cardFidelize.data.repository.contract.ClientRepository
import br.com.rorizinfo.cardFidelize.domain.model.Client

class SaveOrUpdateClientUseCase(
    private val clientRepository: ClientRepository
) {
    
    suspend operator fun invoke(client: Client): Result<Void?> {
        return clientRepository.saveOrUpdateClient(client)
    }
}