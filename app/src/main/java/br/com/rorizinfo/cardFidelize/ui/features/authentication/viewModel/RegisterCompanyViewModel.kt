package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.domain.model.Client
import br.com.rorizinfo.cardFidelize.domain.model.Company
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.SaveOrUpdateCompanyUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateCnpjCompanyUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateNameCompanyUseCase
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerCompany.CompanyEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerCompany.CompanyState
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterCompanyViewModel(
    user: User,
    val validateCnpjCompanyUseCase: ValidateCnpjCompanyUseCase,
    val validateNameCompanyUseCase: ValidateNameCompanyUseCase,
    val saveOrUpdateCompanyUseCase: SaveOrUpdateCompanyUseCase,
    val context: Context
) : ViewModel() {

    private var company = Company(user)

    var stateLiveData = MultipleLiveState<CompanyState>()
    var eventLiveData = SingleLiveEvent<CompanyEvent>()

    fun clearState() {
        stateLiveData = MultipleLiveState()
        eventLiveData = SingleLiveEvent()
    }

    fun validateCnpjField(cnpj: String) {
        this.company.cnpj = cnpj
        updateState { it.copy(enableButton = validateNameCompanyUseCase(cnpj)) }
    }

    fun tapOnNext() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val result = saveOrUpdateCompanyUseCase.invoke(company)
        if (result.isSuccess) {
            CompanyEvent.GoToNextScreen(company)
        } else {
            CompanyEvent.AlertShowMessage(
                context.getString(R.string.message_error_register_name)
            ).run()
        }
        updateState { it.copy(showLoading = false) }
    }

    fun tapOnCancel() {
        CompanyEvent.OnCancel
    }

    private fun updateState(newState: (CompanyState) -> CompanyState) {
        stateLiveData.setValue(newState(stateLiveData.value ?: CompanyState()))
    }

    private fun CompanyEvent.run() {
        eventLiveData.value = this
    }
}