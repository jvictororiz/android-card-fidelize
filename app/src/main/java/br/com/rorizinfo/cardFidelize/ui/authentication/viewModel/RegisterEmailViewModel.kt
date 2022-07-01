package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel

import androidx.lifecycle.ViewModel
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateEmailUseCase
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.choseType.ChoseTypeEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.choseType.ChoseTypeState
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.userEmail.UserEmailEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.userEmail.UserEmailState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent

class RegisterEmailViewModel(
    private val validateEmail: ValidateEmailUseCase
) : ViewModel() {
    
    val stateLiveData = MultipleLiveState<UserEmailState>()
    val eventLiveData = SingleLiveEvent<UserEmailEvent>()
    
    
    fun choseType() {
        updateState { it.copy(enableNext = true) }
    }
    
    fun goToNext(position: Int) {
        if (position == COMPANY_POSITION) {
            ChoseTypeEvent.GoToRegisterCompany.run()
        } else {
            ChoseTypeEvent.GoToRegisterUser.run()
        }
    }
    
    private fun updateState(newState: (ChoseTypeState) -> ChoseTypeState) {
        stateLiveData.setValue(newState(stateLiveData.value ?: ChoseTypeState()))
    }
    
    private fun ChoseTypeEvent.run() {
        eventLiveData.value = this
    }
    
    private companion object {
        private const val COMPANY_POSITION = 0
    }
    
}