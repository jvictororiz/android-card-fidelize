package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.userEmail

sealed class RegisterUserEvent {
    data class ShowAlertMessage(val message: String) : RegisterUserEvent()
    object GoToNext : RegisterUserEvent()
    object GoToBack : RegisterUserEvent()
    
}