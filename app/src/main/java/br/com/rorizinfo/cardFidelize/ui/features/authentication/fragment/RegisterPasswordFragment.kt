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
import br.com.rorizinfo.cardFidelize.databinding.FragmentRegisterPasswordBinding
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.RegisterUserViewModel
import br.com.rorizinfo.cardFidelize.ui.features.authentication.viewModel.model.registerUser.registerUser.RegisterUserEvent
import br.com.rorizinfo.cardFidelize.ui.util.navigateWithAnim
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoard
import br.com.rorizinfo.cardFidelize.ui.util.showKeyBoardView
import br.com.rorizinfo.cardFidelize.ui.util.showMessage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterPasswordFragment : Fragment() {
    private lateinit var binding: FragmentRegisterPasswordBinding
    private val viewModel by sharedViewModel<RegisterUserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPasswordBinding.inflate(inflater, container, false)
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
            viewModel.validatePassword(it.toString())
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
            binding.tvErrorPassword.text = state.errorField
        }

        viewModel.eventLiveData.observe(viewLifecycleOwner) { event ->
            when (event) {
                is RegisterUserEvent.OnCancel -> findNavController().popBackStack(
                    R.id.loginFragment,
                    false
                )
                is RegisterUserEvent.GoToNext -> {
                    findNavController().navigateWithAnim(R.id.toConfirmRegisterPassword)
                }
                is RegisterUserEvent.ShowAlertMessage -> binding.root.showMessage(event.message)
                RegisterUserEvent.GoToBack -> findNavController().popBackStack()
            }
        }
    }

}