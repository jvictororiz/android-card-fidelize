package br.com.rorizinfo.cardFidelize.di

import androidx.biometric.BiometricManager
import br.com.rorizinfo.cardFidelize.data.preference.LocalPreference
import br.com.rorizinfo.cardFidelize.data.repository.UserRepositoryImpl
import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.data.service.contract.UserLoginService
import br.com.rorizinfo.cardFidelize.data.service.firebase.UserLoginServiceFirebase
import br.com.rorizinfo.cardFidelize.domain.usecase.DoLoginUserCase
import br.com.rorizinfo.cardFidelize.domain.usecase.ValidateEmailUseCase
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.LoginViewModel
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.RegisterChoseTypeViewModel
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.RegisterEmailViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modules = module {
    
    //firebase
    single { Firebase.database }
    single { Firebase.auth }
    
    //repository
    single<UserRepository> { UserRepositoryImpl(get()) }

    //usecase
    single { DoLoginUserCase(get()) }
    single { ValidateEmailUseCase() }
    
    //viewmodel
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegisterChoseTypeViewModel() }
    viewModel { RegisterEmailViewModel(get()) }
    
    //service
    single<UserLoginService> { UserLoginServiceFirebase(get(), get()) }
    
    //others
    single { LocalPreference(get()) }
    factory { BiometricManager.from(get()) }
}