package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.login

data class LoginState(
    val hasBiometric: Boolean = false,
    val showLoading: Boolean = false
)