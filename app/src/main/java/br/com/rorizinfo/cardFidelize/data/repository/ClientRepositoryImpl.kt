package br.com.rorizinfo.cardFidelize.data.repository

import br.com.rorizinfo.cardFidelize.data.repository.contract.ClientRepository
import br.com.rorizinfo.cardFidelize.data.repository.mapper.toClientRequest
import br.com.rorizinfo.cardFidelize.data.service.contract.ClientService
import br.com.rorizinfo.cardFidelize.domain.model.Client

class ClientRepositoryImpl(
    private val service: ClientService
) : ClientRepository {
    
    override suspend fun saveOrUpdateClient(client: Client): Result<Void?> {
        return service.saveOrUpdateClient(client.toClientRequest())
    }
}