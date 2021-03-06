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
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterChoseTypeFragment : Fragment() {
    private lateinit var binding: FragmentRegisterChoseTypeBinding
    private val viewModel by viewModel<RegisterChoseTypeViewModel>{
        parametersOf(arguments?.getParcelable(EXTRA_USER))
    }
    
    
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
                ChoseTypeEvent.GoToBack -> findNavController().popBackStack(R.id.loginFragment, false)
                is ChoseTypeEvent.GoToRegisterCompany -> {
                    findNavController().navigateWithAnim(R.id.toComanyRegister, Bundle().apply {
                        putParcelable(RegisterNameCompanyFragment.EXTRA_USER, event.user)
                    })
                }
                is ChoseTypeEvent.GoToRegisterUser -> {
                    findNavController().navigateWithAnim(R.id.toClientRegister, Bundle().apply {
                        putParcelable(RegisterNameUserFragment.EXTRA_USER, event.user)
                    })
                }
            }
        }
    }
    
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }
}