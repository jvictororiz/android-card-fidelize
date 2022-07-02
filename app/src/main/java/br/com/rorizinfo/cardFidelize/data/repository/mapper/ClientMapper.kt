package br.com.rorizinfo.cardFidelize.data.repository.mapper

import br.com.rorizinfo.cardFidelize.data.model.ClientRequest
import br.com.rorizinfo.cardFidelize.domain.model.Client


fun Client.toClientRequest() = ClientRequest(
    userRequest = user.toUserRequest(),
    name = name
)