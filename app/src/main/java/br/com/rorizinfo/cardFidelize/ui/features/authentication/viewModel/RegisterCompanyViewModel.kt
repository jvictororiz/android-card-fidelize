package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel

import androidx.lifecycle.ViewModel
import br.com.rorizinfo.cardFidelize.domain.model.Client
import br.com.rorizinfo.cardFidelize.domain.model.Company
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateCnpjCompanyUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateNameCompanyUseCase
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent

class RegisterCompanyViewModel(
    validateCnpjCompanyUseCase: ValidateCnpjCompanyUseCase,
    validateNameCompanyUseCase: ValidateNameCompanyUseCase,
) : ViewModel() {

    val stateLiveData = MultipleLiveState<NameUserState>()
    val eventLiveData = SingleLiveEvent<NameUserEvent>()


    fun validateCnpjField(toString: String) {
    }

    fun tapOnNext() {
    }

    fun tapOnCancel() {
    }
}