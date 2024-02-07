package com.alxabr.auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.alxabr.auth.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class AuthFragment : Fragment() {

    private val viewModel: AuthVideModel by viewModels()
    private var _binding: AuthFragmentBinding? = null
    private val binding: AuthFragmentBinding
        get() = _binding!!
    private val viewScope: LifecycleCoroutineScope
        get() = viewLifecycleOwner.lifecycleScope

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        AuthFragmentBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            inputName.setText(viewModel.initName)
            inputSurname.setText(viewModel.initSurname)
            inputNumber.setText(viewModel.initNumber)

            viewModel
                .loginButtonEnabled
                .onEach {
                    btnLogin.isEnabled = it && !viewModel.isLoading.value
                }
                .launchIn(viewScope)
            viewModel
                .isLoading
                .onEach {
                    inputName.isEnabled = !it
                    inputSurname.isEnabled = !it
                    inputNumber.isEnabled = !it
                    btnLogin.isEnabled = !it && viewModel.loginButtonEnabled.value
                }
                .launchIn(viewScope)

            inputName.addTextChangedListener {
                viewModel.onUiEvent(AuthUiEvent.OnNameChanged(name = it.toString()))
            }
            inputSurname.addTextChangedListener {
                viewModel.onUiEvent(AuthUiEvent.OnSurnameChanged(surname = it.toString()))
            }
            inputNumber.addTextChangedListener {
                viewModel.onUiEvent(AuthUiEvent.OnNumberChanged(number = it.toString()))
            }
            btnLogin.setOnClickListener {
                viewModel.onUiEvent(AuthUiEvent.OnLoginClick)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}