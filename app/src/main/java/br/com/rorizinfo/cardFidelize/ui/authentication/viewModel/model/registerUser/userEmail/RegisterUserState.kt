package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.userEmail

data class RegisterUserState(
    val showLoading: Boolean = false,
    val enableNextButton: Boolean = false,
    val errorField: String? = ""
)