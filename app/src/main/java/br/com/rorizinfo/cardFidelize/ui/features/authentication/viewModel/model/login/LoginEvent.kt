package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.login

sealed class LoginEvent {
    data class DoLogin(val email: String, val password: String) : LoginEvent()
    data class ShowAlert(val messageError: String) : LoginEvent()
    object OpenDialogBiometric : LoginEvent()
    object GoToForgotPassword : LoginEvent()
    object ClearFields : LoginEvent()
    object GoToHome : LoginEvent()
    object GoToPendingRegister : LoginEvent()
    object GoToRegister : LoginEvent()
}