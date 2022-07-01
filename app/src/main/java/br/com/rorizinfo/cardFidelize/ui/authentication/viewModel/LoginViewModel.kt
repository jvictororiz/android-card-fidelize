package br.com.rorizinfo.cardFidelize.ui.authentication.viewModel

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.data.preference.BasePreference
import br.com.rorizinfo.cardFidelize.data.preference.LocalPreference
import br.com.rorizinfo.cardFidelize.domain.usecase.DoLoginUserCase
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.login.LoginEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.login.LoginState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(
    private val localPreference: LocalPreference,
    private val loginUserCase: DoLoginUserCase,
    private val context: Context,
    biometricManager: BiometricManager
) : ViewModel() {
    
    val stateLiveData = MultipleLiveState<LoginState>()
    val eventLiveData = SingleLiveEvent<LoginEvent>()
    
    init {
        checkBiometric(biometricManager)
    }
    
    private fun checkBiometric(biometricManager: BiometricManager) = viewModelScope.launch {
        val user = localPreference.get(BasePreference.EmailUser)
        val password = localPreference.get(BasePreference.PasswordUser)
        val deviceWithBiometric =
            biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
        val enableBiometric = user.isNotEmpty() && password.isNotEmpty() && deviceWithBiometric
        updateState { it.copy(hasBiometric = enableBiometric) }
    }
    
    fun tapOnBiometric() {
        LoginEvent.OpenDialogBiometric.run()
    }
    
    fun doLogin(email: String, password: String) = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val resultLogin = loginUserCase(email, password)
        updateState { it.copy(showLoading = false) }
    }
    
    fun loginWithBiometric() = viewModelScope.launch {
        updateState { it.copy(showLoading = true) }
        val emailUser = localPreference.get(BasePreference.EmailUser)
        val passwordUser = localPreference.get(BasePreference.PasswordUser)
        doLogin(emailUser, passwordUser)
    }
    
    fun tapOnRegister() {
        LoginEvent.GoToRegister.run()
    }
    
    fun errorLoginWithFingerprint() {
        LoginEvent.ShowAlert(context.getString(R.string.error_login_fingerprint)).run()
    }
    
    private fun updateState(newState: (LoginState) -> LoginState) {
        stateLiveData.setValue(newState(stateLiveData.value ?: LoginState()))
    }
    
    private fun LoginEvent.run() {
        eventLiveData.setValue(this)
    }
    
}