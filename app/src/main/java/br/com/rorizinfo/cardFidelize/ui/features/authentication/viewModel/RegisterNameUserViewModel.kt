package br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.domain.model.Client
import br.com.rorizinfo.cardFidelize.domain.model.User
import br.com.rorizinfo.cardFidelize.domain.usecase.SaveOrUpdateClientUseCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateNameUseCase
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserState
import br.com.rorizinfo.cardFidelize.ui.util.MultipleLiveState
import br.com.rorizinfo.cardFidelize.ui.util.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterNameUserViewModel(
    user: User,
    private val validateNameUseCase: ValidateNameUseCase,
    private val saveOrUpdateClientUseCase: SaveOrUpdateClientUseCase,
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
        updateState { it.copy(showLoading = true) }
        val result = saveOrUpdateClientUseCase(client)
        if (result.isSuccess) {
            NameUserEvent.GoToHome(client).run()
        } else {
            NameUserEvent.AlertShowMessage(
                context.getString(R.string.message_error_register_name)
            ).run()
        }
        updateState { it.copy(showLoading = false) }

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