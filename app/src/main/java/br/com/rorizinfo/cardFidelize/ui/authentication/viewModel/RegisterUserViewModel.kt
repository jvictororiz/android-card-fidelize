package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateEmailUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.VerifyEmailAlreadyExistsUseCase
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.registerUser.RegisterUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val verifyEmailAlreadyExistsUseCase: VerifyEmailAlreadyExistsUseCase,
    private val context: Context
) : ViewModel() {
    
    val stateLiveData = MultipleLiveState<RegisterUserState>()
    val eventLiveData = SingleLiveEvent<RegisterUserEvent>()
    
    private val user = User()
    
    
    fun validateEmailField(email: String) {
        this.user.email = email
        updateState { it.copy(enableNextButton = validateEmailUseCase(email)) }
    }
    
    fun tapOnCancel(){
        RegisterUserEvent.GoToBack.run()
    }
    
    fun tapOnNext() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val emailAlreadyExists = verifyEmailAlreadyExistsUseCase(user.email)
        if (emailAlreadyExists) {
            RegisterUserEvent.ShowAlertMessage(context.getString(R.string.emailAlreadyExists)).run()
        } else {
            RegisterUserEvent.GoToNext.run()
        }
        updateState { it.copy(showLoading = false) }
    }
    
    private fun updateState(newStateRegister: (RegisterUserState) -> RegisterUserState) {
        stateLiveData.setValue(newStateRegister(stateLiveData.value ?: RegisterUserState()))
    }
    
    private fun RegisterUserEvent.run() {
        eventLiveData.value = this
    }
}