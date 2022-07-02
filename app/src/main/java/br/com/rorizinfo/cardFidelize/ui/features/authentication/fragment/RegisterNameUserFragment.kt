package br.com.rorizinfo.cardFidelize.ui.features.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterNameUserBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterNameUserViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.nameUser.NameUserEvent
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class RegisterNameUserFragment : Fragment() {
    private lateinit var binding: FragmentRegisterNameUserBinding
    private val viewModel by sharedViewModel<RegisterNameUserViewModel> {
        parametersOf(arguments?.getParcelable(EXTRA_USER))
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterNameUserBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }
    
    private fun setupListeners() {
        binding.edtName.addTextChangedListener {
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
            binding.btnNext.isEnabled = state.enableButton
            binding.btnNext.isVisible = !state.showLoading
            binding.pbLoading.isVisible = state.showLoading
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                NameUserEvent.GoToBack -> findNavController().popBackStack()
                is NameUserEvent.GoToHome -> {
                    findNavController().navigateWithAnim(R.id.toHome, launcherSingleTop = true)
                }
                is NameUserEvent.OnCancel -> findNavController().popBackStack(R.id.loginFragment, false)
                is NameUserEvent.AlertShowMessage -> binding.root.showMessage(event.message)
            }
        }
    }
    
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }
}