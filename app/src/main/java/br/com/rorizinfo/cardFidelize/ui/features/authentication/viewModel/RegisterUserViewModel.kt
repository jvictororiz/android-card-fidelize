package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.data.preference.BasePreference
import br.com.rorizinfo.cardFidelize.data.preference.LocalPreference
import br.com.rorizinfo.cardFidelize.data.service.firebase.exception.AccountAlreadyExists
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.*
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser.RegisterUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch


class RegisterUserViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val verifyEmailAlreadyExistsUseCase: VerifyEmailAlreadyExistsUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val confirmPasswordUseCase: ConfirmPasswordUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val verifyValidationAccountUseCase: VerifyValidationAccountUseCase,
    private val localPreference: LocalPreference,
    private val context: Context
) : ViewModel() {

    var stateLiveData = MultipleLiveState<RegisterUserState>()
    var eventLiveData = SingleLiveEvent<RegisterUserEvent>()

    private val user = User()


    fun clearState() {
        stateLiveData = MultipleLiveState()
        eventLiveData = SingleLiveEvent()
    }

    fun validateEmailField(email: String) {
        this.user.email = email
        updateState { it.copy(enableNextButton = validateEmailUseCase(email)) }
    }

    fun validatePassword(password: String) {
        user.password = password
        val result = validatePasswordUseCase.validatePassword(password)
        if (result.isSuccess) {
            updateState { it.copy(errorField = null) }
            updateState { it.copy(enableNextButton = true) }
        } else {
            result.exceptionOrNull()?.message?.let {
                updateState { it.copy(enableNextButton = false) }
                updateState { it.copy(errorField = result.exceptionOrNull()?.message) }
            }
        }
    }

    fun validateConfirmPassword(password: String) {
        val result = confirmPasswordUseCase.confirmPassword(
            currentPassword = user.password,
            password = password
        )
        if (result.isSuccess) {
            updateState { it.copy(errorField = null) }
            updateState { it.copy(enableNextButton = true) }
        } else {
            updateState { it.copy(enableNextButton = false) }
            updateState { it.copy(errorField = result.exceptionOrNull()?.message) }
        }
    }

    fun tapOnNext() {
        RegisterUserEvent.GoToNext.run()
    }

    fun sendEmailVerification() = viewModelScope.launch {
        resetTime()
        val result = sendEmailVerificationUseCase()
        if (result.isSuccess) {
            RegisterUserEvent.ShowAlertMessage(
                context.getString(R.string.success_send_email)
            ).run()
        } else {
            RegisterUserEvent.ShowAlertMessage(
                context.getString(R.string.fail_send_email)
            ).run()
        }
    }

    private fun resetTime() {
        val initValue = 30L
        updateState { it.copy(enableNextButton = false, countSendEmail = "30") }
        object : CountDownTimer(initValue * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateState { it.copy(countSendEmail = (millisUntilFinished / 1000).toString()) }
            }
            
            override fun onFinish() {
                updateState { it.copy(enableNextButton = true) }
            }
        }.start()
    }
    
    fun tapOnVerifyValidationEmail() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        if (verifyValidationAccountUseCase(user.email, user.password)) {
            localPreference.save(BasePreference.EmailUser, user.email)
            localPreference.save(BasePreference.PasswordUser, user.password)
            RegisterUserEvent.GoToNext.run()
        } else {
            RegisterUserEvent.ShowAlertMessage(context.getString(R.string.email_not_validate)).run()
        }
        updateState { it.copy(showLoading = false) }
    }
    
    fun tapOnNextConfirmRegisterPassword() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val result = saveUserUseCase(user)
        if (result.isSuccess) {
            RegisterUserEvent.GoToNext.run()
        } else {
            if (result.exceptionOrNull() is AccountAlreadyExists) {
                updateState {
                    it.copy(
                        errorField = context.getString(R.string.emailAlreadyExists)
                    )
                }
            } else {
                updateState {
                    it.copy(
                        errorField = context.getString(
                            R.string.registration_confirm_register_password_error
                        )
                    )
                }
            }
        }
        updateState { it.copy(showLoading = false) }
    }
    
    fun tapOnCancel() {
        RegisterUserEvent.OnCancel.run()
    }
    
    fun tapOnBack() {
        RegisterUserEvent.GoToBack.run()
    }
    
    fun tapOnNextEmail() = viewModelScope.launch {
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