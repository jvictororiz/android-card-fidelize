package br.com.rorizinfo.cardFidelize.ui.features.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterConfirmEmailBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterUserViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import br.com.rorizinfo.cardFidelize.ui.util.setTextHtml
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel

class RegisterValidationEmailFragment : Fragment() {
    private lateinit var binding: FragmentRegisterConfirmEmailBinding
    private val viewModel by sharedStateViewModel<RegisterUserViewModel>()
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterConfirmEmailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clearState()
        viewModel.sendEmailVerification()
        setupListeners()
        setupObservers()
    }
    
    private fun setupListeners() {
        binding.btnNext.setOnClickListener {
            viewModel.tapOnVerifyValidationEmail()
        }
        
        binding.btnCancel.setOnClickListener {
            viewModel.tapOnCancel()
        }
        
        binding.btnSendEmail.setOnClickListener {
            viewModel.sendEmailVerification()
        }
    }
    
    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.btnNext.isVisible = !state.showLoading
            binding.pbLoading.isVisible = state.showLoading
            binding.btnSendEmail.isEnabled = state.enableNextButton
            if (state.enableNextButton) {
                binding.btnSendEmail.setTextHtml(getString(R.string.send_email))
            } else {
                binding.btnSendEmail.setTextHtml(getString(R.string.time_send_email, state.countSendEmail))
            }
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is RegisterUserEvent.GoToBack -> findNavController().popBackStack()
                is RegisterUserEvent.ShowAlertMessage -> binding.root.showMessage(event.message)
                is RegisterUserEvent.FinishRegisterUser -> {
                    findNavController().navigateWithAnim(R.id.goToSelectType, Bundle().apply {
                        putParcelable(RegisterChoseTypeFragment.EXTRA_USER, event.user)
                    })
                }
                RegisterUserEvent.OnCancel -> {
                    findNavController().popBackStack(R.id.loginFragment, false)
                }
            }
        }
    }
}