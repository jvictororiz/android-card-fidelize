package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.domain.usecase.SendResetPasswordUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateEmailUseCase
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.resetPassword.ResetPasswordEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.resetPassword.ResetPasswordState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val resetPasswordUseCase: SendResetPasswordUseCase,
    private val context: Context
) : ViewModel() {
    val stateLiveData = MultipleLiveState<ResetPasswordState>()
    val eventLiveData = SingleLiveEvent<ResetPasswordEvent>()
    
    var email: String = ""
    
    fun validateEmailField(email: String) {
        this.email = email
        updateState { it.copy(enableNextButton = validateEmailUseCase(email)) }
    }
    
    fun tapOnNext() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val result = resetPasswordUseCase(email)
        if (result.isSuccess) {
            ResetPasswordEvent.ShowAlertMessage(
                context.getString(R.string.message_send_reset_password_email)
            ).run()
            updateState { it.copy(showLoading = false) }
            delay(TIME_ON_BACK)
            ResetPasswordEvent.GoToBack.run()
        } else {
            updateState { it.copy(showLoading = false) }
            ResetPasswordEvent.ShowAlertMessage(
                context.getString(R.string.error_send_email)
            ).run()
        }
    }
    
    fun tapOnBack() {
        ResetPasswordEvent.GoToBack.run()
    }
    
    private fun updateState(newState: (ResetPasswordState) -> ResetPasswordState) {
        stateLiveData.setValue(newState(stateLiveData.value ?: ResetPasswordState()))
    }
    
    private fun ResetPasswordEvent.run() {
        eventLiveData.value = this
    }
    
    private companion object {
        private const val TIME_ON_BACK = 1500L
    }
}