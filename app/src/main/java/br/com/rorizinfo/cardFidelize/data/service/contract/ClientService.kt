package br.com.rorizinfo.cardFidelize.data.service.contract

import br.com.rorizinfo.cardFidelize.data.model.ClientRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserRequest
import br.com.rorizinfo.cardFidelize.data.model.RegisterUserResponse

interface ClientService {
    suspend fun saveOrUpdateClient(clientRequest: ClientRequest): Result<Void?>
}