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
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterEmailBinding
import br.com.rorizinfo.cardFidelize.databinding.FragmentResetPasswordBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterUserViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.ResetPasswordViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.resetPassword.ResetPasswordEvent
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoard
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoardView
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentResetPasswordBinding
    private val viewModel by sharedViewModel<ResetPasswordViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        binding.edtLogin.showKeyBoard()
    }

    private fun setupListeners() {
        binding.root.setOnClickListener {
            binding.root.showKeyBoardView(binding.edtLogin)
        }

        binding.edtLogin.addTextChangedListener {
            viewModel.validateEmailField(it.toString())
        }

        binding.btnNext.setOnClickListener {
            viewModel.tapOnNext()
        }

        binding.btnCancel.setOnClickListener {
            viewModel.tapOnBack()
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
                is ResetPasswordEvent.GoToBack -> findNavController().popBackStack()
                is ResetPasswordEvent.ShowAlertMessage -> binding.root.showMessage(event.message)
            }
        }
    }
}