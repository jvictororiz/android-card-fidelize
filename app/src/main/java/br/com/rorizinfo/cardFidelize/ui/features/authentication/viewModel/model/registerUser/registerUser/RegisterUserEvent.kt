package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser

import br.com.rorizinfo.cardFidelize.domain.model.User

sealed class RegisterUserEvent {
    data class ShowAlertMessage(val message: String) : RegisterUserEvent()
    object GoToNext : RegisterUserEvent()
    object GoToBack : RegisterUserEvent()
    object OnCancel : RegisterUserEvent()
    data class FinishRegisterUser(val user: User) : RegisterUserEvent()
    
}