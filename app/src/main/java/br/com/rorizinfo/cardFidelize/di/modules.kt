package br.com.rorizinfo.cardFidelize.di

import androidx.biometric.BiometricManager
import br.com.rorizinfo.cardFidelize.data.preference.LocalPreference
import br.com.rorizinfo.cardFidelize.data.repository.UserRepositoryImpl
import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.data.service.contract.UserLoginService
import br.com.rorizinfo.cardFidelize.data.service.firebase.UserLoginServiceFirebase
import br.com.rorizinfo.cardFidelize.domain.usecase.*
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.LoginViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterChoseTypeViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterNameUserViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterUserViewModel
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
    single { ValidateNameUseCase() }
    single { ValidatePasswordUseCase(get()) }
    single { ConfirmPasswordUseCase(get()) }
    single { ConfirmPasswordResetUseCase(get()) }
    single { VerifyEmailAlreadyExistsUseCase(get()) }
    single { SendEmailVerificationUseCase(get()) }
    single { SaveUserUseCase(get()) }
    single { VerifyValidationAccountUseCase(get()) }
    
    //viewmodel
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { RegisterChoseTypeViewModel() }
    viewModel { RegisterUserViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { params -> RegisterNameUserViewModel(params.get(), get(), get()) }
    
    //service
    single<UserLoginService> { UserLoginServiceFirebase(get(), get()) }
    
    //others
    single { LocalPreference(get()) }
    factory { BiometricManager.from(get()) }
}