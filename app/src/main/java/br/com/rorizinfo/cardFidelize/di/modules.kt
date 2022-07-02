package br.com.rorizinfo.cardFidelize.di

import androidx.biometric.BiometricManager
import br.com.rorizinfo.cardFidelize.data.preference.LocalPreference
import br.com.rorizinfo.cardFidelize.data.repository.ClientRepositoryImpl
import br.com.rorizinfo.cardFidelize.data.repository.CompanyRepositoryImpl
import br.com.rorizinfo.cardFidelize.data.repository.UserRepositoryImpl
import br.com.rorizinfo.cardFidelize.data.repository.contract.ClientRepository
import br.com.rorizinfo.cardFidelize.data.repository.contract.CompanyRepository
import br.com.rorizinfo.cardFidelize.data.repository.contract.UserRepository
import br.com.rorizinfo.cardFidelize.data.service.contract.ClientService
import br.com.rorizinfo.cardFidelize.data.service.contract.CompanyService
import br.com.rorizinfo.cardFidelize.data.service.contract.UserLoginService
import br.com.rorizinfo.cardFidelize.data.service.firebase.ClientServiceFirebase
import br.com.rorizinfo.cardFidelize.data.service.firebase.CompanyServiceFirebase
import br.com.rorizinfo.cardFidelize.data.service.firebase.UserLoginServiceFirebase
import br.com.rorizinfo.cardFidelize.domain.usecase.*
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.LoginViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterChoseTypeViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterCompanyViewModel
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
    single<CompanyRepository> { CompanyRepositoryImpl(get()) }
    single<ClientRepository> { ClientRepositoryImpl(get()) }

    //usecase
    single { DoLoginUserCase(get()) }
    single { ValidateEmailUseCase() }
    single { ValidateNameUseCase() }
    single { SaveOrUpdateClientUseCase(get()) }
    single { ValidatePasswordUseCase(get()) }
    single { ConfirmPasswordUseCase(get()) }
    single { ConfirmPasswordResetUseCase(get()) }
    single { VerifyEmailAlreadyExistsUseCase(get()) }
    single { SendEmailVerificationUseCase(get()) }
    single { SaveUserUseCase(get()) }
    single { VerifyValidationAccountUseCase(get()) }
    single { ValidateNameCompanyUseCase(get()) }
    single { ValidateCnpjCompanyUseCase(get()) }
    single { SaveOrUpdateCompanyUseCase(get()) }

    //viewmodel
    viewModel { LoginViewModel(get(), get(), get(), get()) }
    viewModel { params -> RegisterChoseTypeViewModel(params.get()) }
    viewModel {  params -> RegisterCompanyViewModel(params.get(), get()) }
    viewModel {
        RegisterUserViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel { params -> RegisterNameUserViewModel(params.get(), get(), get(), get()) }

    //service
    single<UserLoginService> { UserLoginServiceFirebase(get(), get()) }
    single<ClientService> { ClientServiceFirebase(get()) }
    single<CompanyService> { CompanyServiceFirebase(get()) }


    //others
    single { LocalPreference(get()) }
    factory { BiometricManager.from(get()) }
}