package br.com.rorizinfo.cardFidelize.data.repository.contract

import br.com.rorizinfo.cardFidelize.data.model.ClientRequest
import br.com.rorizinfo.cardFidelize.domain.model.Client

interface ClientRepository {
    suspend fun saveOrUpdateClient(client: Client): Result<Void?>
}