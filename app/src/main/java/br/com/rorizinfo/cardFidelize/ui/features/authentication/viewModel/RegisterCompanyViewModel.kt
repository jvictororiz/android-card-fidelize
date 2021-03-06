package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.domain.model.Company
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.SaveOrUpdateCompanyUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateCnpjCompanyUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateNameCompanyUseCase
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerCompany.CompanyEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerCompany.CompanyState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterCompanyViewModel(
    user: User,
    private val validateCnpjCompanyUseCase: ValidateCnpjCompanyUseCase,
    private val validateNameCompanyUseCase: ValidateNameCompanyUseCase,
    private val saveOrUpdateCompanyUseCase: SaveOrUpdateCompanyUseCase,
    val context: Context
) : ViewModel() {
    
    private var company = Company(user.id, user.email)
    
    var stateLiveData = MultipleLiveState<CompanyState>()
    var eventLiveData = SingleLiveEvent<CompanyEvent>()
    
    fun clearState() {
        stateLiveData = MultipleLiveState()
        eventLiveData = SingleLiveEvent()
    }
    
    fun validateCnpjField(cnpj: String) {
        this.company.cnpj = cnpj
        updateState { it.copy(enableButton = validateCnpjCompanyUseCase(cnpj)) }
    }
    
    fun validateNameField(nameCompany: String) {
        this.company.nameCompany = nameCompany
        updateState { it.copy(enableButton = validateNameCompanyUseCase(nameCompany)) }
    }
    
    fun tapOnNext() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val result = saveOrUpdateCompanyUseCase.invoke(company)
        if (result.isSuccess) {
            CompanyEvent.GoToNextScreen(company).run()
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