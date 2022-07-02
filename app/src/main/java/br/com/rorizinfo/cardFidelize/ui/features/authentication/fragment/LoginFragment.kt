package br.com.rorizinfo.cardFidelize.ui.features.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.databinding.FragmentLoginBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.LoginViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.login.LoginEvent
import br.com.rorizinfo.cardFidelize.ui.component.FingerPrintView
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val fingerprint by lazy { FingerPrintView(this) }
    private val viewModel by viewModel<LoginViewModel>()
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }
    
    private fun setupListeners() {
        
        binding.btnLogin.setOnClickListener {
            viewModel.doLogin(
                binding.edtLogin.text.toString(),
                binding.edtPassword.text.toString()
            )
        }
        
        binding.btnForgotPassword.setOnClickListener {
            viewModel.tapOnForgotPassword()
        }
        
        binding.btnBiometric.setOnClickListener {
            viewModel.tapOnBiometric()
        }
        
        binding.btnRegister.setOnClickListener {
            viewModel.tapOnRegister()
        }
    }
    
    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.labelBiometric.isVisible = state.hasBiometric
            binding.btnBiometric.isVisible = state.hasBiometric && !state.showLoading
            binding.pbLoading.isVisible = state.showLoading
            binding.btnLogin.isVisible = !state.showLoading
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is LoginEvent.DoLogin -> {
                }
                LoginEvent.GoToForgotPassword -> {
                    findNavController().navigateWithAnim(R.id.toResetPassword)
                }
                LoginEvent.GoToHome -> findNavController().navigateWithAnim(R.id.homeUserActivity)
                LoginEvent.GoToRegister -> {
                    findNavController().navigateWithAnim(R.id.toRegisterEmailFragment)
                }
                LoginEvent.OpenDialogBiometric -> {
                    fingerprint.showDialogBiometric(
                        successCallBack = {
                            viewModel.loginWithBiometric()
                        },
                        errorCallback = {
                            viewModel.errorLoginWithFingerprint()
                        }
                    )
                }
                is LoginEvent.ShowAlert -> {
                    binding.root.showMessage(event.messageError)
                }
                LoginEvent.GoToPendingRegister -> {
                    findNavController().navigateWithAnim(R.id.registerValidationEmailFragment)
                }
                LoginEvent.ClearFields -> {
                    binding.edtLogin.setText("")
                    binding.edtPassword.setText("")
                }
            }
        }
    }
}