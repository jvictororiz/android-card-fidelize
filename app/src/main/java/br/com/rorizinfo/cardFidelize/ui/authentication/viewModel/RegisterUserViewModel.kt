package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.ConfirmPasswordUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.SaveUserUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.SendEmailVerificationUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateEmailUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidatePasswordUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.VerifyEmailAlreadyExistsUseCase
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.registerUser.RegisterUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val verifyEmailAlreadyExistsUseCase: VerifyEmailAlreadyExistsUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val confirmPasswordUseCase: ConfirmPasswordUseCase,
    private val sendEmailVerificationUseCase: SendEmailVerificationUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val context: Context
) : ViewModel() {

    val stateLiveData = MultipleLiveState<RegisterUserState>()
    val eventLiveData = SingleLiveEvent<RegisterUserEvent>()

    private val user = User()


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
            updateState { it.copy(errorField = result.exceptionOrNull()?.message) }
        }
    }

    fun tapOnNext() {
        RegisterUserEvent.GoToNext
    }

    fun tapOnNextConfirmRegisterPassword() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val result = saveUserUseCase(user)
        if (result.isSuccess) {
            RegisterUserEvent.GoToNext
        } else {
            updateState {
                it.copy(
                    errorField = context.getString(
                        R.string.registration_confirm_register_password_error
                    )
                )
            }
        }
        updateState { it.copy(showLoading = false) }
    }

    fun tapOnCancel() {
        RegisterUserEvent.OnCancel
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