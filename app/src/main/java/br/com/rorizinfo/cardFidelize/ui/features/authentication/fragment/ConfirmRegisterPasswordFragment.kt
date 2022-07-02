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
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterConfirmPasswordBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterUserViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoard
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoardView
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ConfirmRegisterPasswordFragment : Fragment() {
    private lateinit var binding: FragmentRegisterConfirmPasswordBinding
    private val viewModel by sharedViewModel<RegisterUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterConfirmPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clearState()
        setupListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        binding.edtPassword.showKeyBoard()
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            binding.root.showKeyBoardView(binding.edtPassword)

        }
        binding.edtPassword.addTextChangedListener {
            viewModel.validateConfirmPassword(it.toString())
        }

        binding.btnNext.setOnClickListener {
            viewModel.tapOnNextConfirmRegisterPassword()
        }

        binding.btnBack.setOnClickListener {
            viewModel.tapOnBack()
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
            binding.tvErrorPassword.text = state.errorField
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is RegisterUserEvent.GoToBack -> findNavController().popBackStack()
                is RegisterUserEvent.GoToNext -> {
                    findNavController().navigate(R.id.toValidationEmail)
                }
                is RegisterUserEvent.ShowAlertMessage -> binding.root.showMessage(event.message)
                is RegisterUserEvent.OnCancel -> findNavController().popBackStack(
                    R.id.loginFragment,
                    false
                )
            }
        }
    }

}