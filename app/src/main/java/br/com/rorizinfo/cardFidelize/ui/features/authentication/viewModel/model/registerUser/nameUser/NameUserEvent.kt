package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser

import br.com.rorizinfo.cardFidelize.domain.model.Client

sealed class NameUserEvent {
    object GoToBack : NameUserEvent()
    object OnCancel : NameUserEvent()
    data class GoToHome(val client: Client): NameUserEvent()
}