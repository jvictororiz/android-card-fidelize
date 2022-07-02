package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerCompany

import br.com.rorizinfo.cardFidelize.domain.model.Client
import br.com.rorizinfo.cardFidelize.domain.model.Company
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserEvent

sealed class CompanyEvent {
    object GoToBack : CompanyEvent()
    object OnCancel : CompanyEvent()
    data class AlertShowMessage(val message: String) : CompanyEvent()
    data class GoToNextScreen(val company: Company) : CompanyEvent()
    
}