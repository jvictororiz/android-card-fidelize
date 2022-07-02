package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser

sealed class RegisterUserEvent {
    data class ShowAlertMessage(val message: String) : RegisterUserEvent()
    object GoToNext : RegisterUserEvent()
    object GoToBack : RegisterUserEvent()
    object OnCancel : RegisterUserEvent()

}