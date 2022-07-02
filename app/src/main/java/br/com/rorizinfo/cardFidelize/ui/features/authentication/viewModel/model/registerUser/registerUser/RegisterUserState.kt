package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser

data class RegisterUserState(
    val showLoading: Boolean = false,
    val enableNextButton: Boolean = false,
    val errorField: String? = "",
    val countSendEmail: String = ""
)