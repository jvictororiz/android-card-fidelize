package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.login

sealed class LoginEvent {
    data class DoLogin(val email: String, val password: String) : LoginEvent()
    data class ShowAlert(val messageError: String) : LoginEvent()
    object SuccessBiometric : LoginEvent()
    object OpenDialogBiometric : LoginEvent()
    object GoToForgotPassword : LoginEvent()
    object GoToHome : LoginEvent()
    object GoToRegister : LoginEvent()
}