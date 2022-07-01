package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.domain.model.Client
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateNameUseCase
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.nameUser.NameUserEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.nameUser.NameUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterNameUserViewModel(
    user: User,
    private val validateNameUseCase: ValidateNameUseCase,
    private val context: Context
) : ViewModel() {
    
    private val client = Client(user)
    
    val stateLiveData = MultipleLiveState<NameUserState>()
    val eventLiveData = SingleLiveEvent<NameUserEvent>()
    
    fun validateEmailField(name: String) {
        client.name = name
        updateState { it.copy(enableButton = validateNameUseCase(name)) }
    }
    
    fun tapOnNext() = viewModelScope.launch {
        NameUserEvent.GoToHome(client).run()
    }
    
    fun tapOnCancel() {
        NameUserEvent.OnCancel.run()
    }
    
    private fun updateState(newState: (NameUserState) -> NameUserState) {
        stateLiveData.setValue(newState(stateLiveData.value ?: NameUserState()))
    }
    
    private fun NameUserEvent.run() {
        eventLiveData.value = this
    }
}