package com.alxabr.auth_presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.alxabr.auth_presentation.databinding.AuthFragmentBinding
import com.alxabr.auth_presentation.feature.AuthFeatureImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class AuthFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()
    private val authFeature: AuthFeatureImpl by activityViewModels()
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
            inputName.setText(viewModel.inputNameValue.value)
            viewModel
                .inputNameEnabled
                .onEach(inputName::setEnabled)
                .launchIn(viewScope)
            viewModel
                .inputNameError
                .onEach {
                    if (it) {
                        inputName.error = "Недопустимое значение ошибки"
                    } else {
                        inputName.error = null
                    }
                }
                .launchIn(viewScope)

            inputSurname.setText(viewModel.inputSurnameValue.value)
            viewModel
                .inputSurnameEnabled
                .onEach(inputSurname::setEnabled)
                .launchIn(viewScope)
            viewModel
                .inputSurnameError
                .onEach {
                    if (it) {
                        inputSurname.error = "Недопустимое значение ошибки"
                    } else {
                        inputSurname.error = null
                    }
                }
                .launchIn(viewScope)

            inputNumber.setText(viewModel.inputNumberValue.value)
            viewModel
                .inputNumberEnabled
                .onEach(inputNumber::setEnabled)
                .launchIn(viewScope)
            viewModel
                .inputNumberError
                .onEach {
                    if (it) {
                        inputNumber.error = "Недопустимое значение ошибки"
                    } else {
                        inputNumber.error = null
                    }
                }
                .launchIn(viewScope)

            viewModel
                .buttonLoginEnabled
                .onEach(btnLogin::setEnabled)
                .launchIn(viewScope)

            viewModel
                .onAuthSuccess
                .onEach { authFeature.onAuthSuccess() }
                .launchIn(viewModel.viewModelScope)

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