package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.login

data class LoginState(
    val hasBiometric: Boolean = false,
    val showLoading: Boolean = false
)