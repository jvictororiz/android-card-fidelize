package br.com.rorizinfo.cardFidelize.ui.features.authentication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.rorizinfo.cardFidelize.R
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterChoseTypeBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterChoseTypeViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.choseType.ChoseTypeEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterChoseTypeFragment : Fragment() {
    private lateinit var binding: FragmentRegisterChoseTypeBinding
    private val viewModel by viewModel<RegisterChoseTypeViewModel>()
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterChoseTypeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }
    
    private fun setupListeners() {
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            viewModel.choseType()
        }
        
        binding.btnNext.setOnClickListener {
            when (binding.radioGroup.checkedRadioButtonId) {
                R.id.buttonCompany -> viewModel.goToNext(0)
                R.id.buttonClient -> viewModel.goToNext(1)
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
            binding.btnNext.isEnabled = state.enableNext
        }
        
        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                ChoseTypeEvent.GoToBack -> findNavController().popBackStack()
                ChoseTypeEvent.GoToRegisterCompany -> TODO()
                ChoseTypeEvent.GoToRegisterUser -> TODO()
            }
        }
    }
}