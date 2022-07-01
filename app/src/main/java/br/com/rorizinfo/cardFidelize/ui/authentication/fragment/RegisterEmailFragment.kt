package br.com.rorizinfo.cardFidelize.ui.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterEmailBinding
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.RegisterUserViewModel
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.choseType.ChoseTypeEvent
import br.com.rorizinfo.cardFidelize.ui.authentication.viewModel.model.registerUser.userEmail.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterEmailFragment : Fragment() {
    private lateinit var binding: FragmentRegisterEmailBinding
    private val viewModel by viewModel<RegisterUserViewModel>()
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterEmailBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }
    
    private fun setupListeners() {
        binding.edtLogin.addTextChangedListener {
            viewModel.validateEmailField(it.toString())
        }
        
        binding.btnNext.setOnClickListener {
            viewModel.tapOnNext()
        }
        
        binding.btnCancel.setOnClickListener {
            viewModel.tapOnCancel()
        }
    }
    
    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.btnNext.isEnabled = state.enableNextButton
            binding.btnNext.isVisible = !state.showLoading
            binding.pbLoading.isVisible = state.showLoading
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is RegisterUserEvent.GoToBack -> findNavController().popBackStack()
                is RegisterUserEvent.GoToNext -> {
//                    findNavController().navigate(R.id.)
                }
                is RegisterUserEvent.ShowAlertMessage -> binding.root.showMessage(event.message)
            }
        }
    }
}