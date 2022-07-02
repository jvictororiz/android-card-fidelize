package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.resetPassword

sealed class ResetPasswordEvent {
    data class ShowAlertMessage(val message: String) : ResetPasswordEvent()
    object GoToBack : ResetPasswordEvent()
}