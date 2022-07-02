package br.com.rorizinfo.cardFidelize.ui.features.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterCnpjCompanyBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterCompanyViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerCompany.CompanyEvent
import br.com.rorizinfo.cardFidelize.ui.util.applyCnpjMask
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoard
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoardView
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterCnpjCompanyFrgament : Fragment() {
    private lateinit var binding: FragmentRegisterCnpjCompanyBinding
    private val viewModel by sharedViewModel<RegisterCompanyViewModel>()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterCnpjCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clearState()
        configViews()
        setupListeners()
        setupObservers()
    }
    
    override fun onResume() {
        super.onResume()
        
        binding.edtCnpj.showKeyBoard()
    }
    
    private fun configViews() {
        binding.edtCnpj.applyCnpjMask()
    }
    
    private fun setupListeners() {
        binding.root.setOnClickListener {
            binding.root.showKeyBoardView(binding.edtCnpj)
        }
        
        binding.edtCnpj.addTextChangedListener {
            viewModel.validateCnpjField(it.toString())
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
            binding.btnNext.isEnabled = state.enableButton
            binding.btnNext.isVisible = !state.showLoading
            binding.pbLoading.isVisible = state.showLoading
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                CompanyEvent.GoToBack -> findNavController().popBackStack()
                is CompanyEvent.GoToNextScreen -> {
                    findNavController().navigateWithAnim(R.id.toHomeCompany)
                    requireActivity().finish()
                }
                is CompanyEvent.OnCancel -> findNavController().popBackStack(
                    R.id.loginFragment,
                    false
                )
                is CompanyEvent.AlertShowMessage -> binding.root.showMessage(event.message)
            }
        }
    }
    
    companion object {
        private const val EXTRA_USER = "EXTRA_USER"
    }
}