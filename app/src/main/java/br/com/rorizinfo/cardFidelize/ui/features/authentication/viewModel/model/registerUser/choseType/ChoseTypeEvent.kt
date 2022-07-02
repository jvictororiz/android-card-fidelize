package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.choseType

import br.com.rorizinfo.cardFidelize.domain.model.User

sealed class ChoseTypeEvent {
    data class GoToRegisterUser(val user: User) : ChoseTypeEvent()
    data class GoToRegisterCompany(val user: User) : ChoseTypeEvent()
    object GoToBack : ChoseTypeEvent()
}