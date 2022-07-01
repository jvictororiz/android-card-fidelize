package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.choseType

sealed class ChoseTypeEvent {
    object GoToRegisterUser : ChoseTypeEvent()
    object GoToRegisterCompany : ChoseTypeEvent()
    object GoToBack : ChoseTypeEvent()
}