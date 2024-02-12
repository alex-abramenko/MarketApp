package com.alxabr.auth_presentation.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.alxabr.auth_presentation.databinding.AuthFragmentBinding
import com.alxabr.auth_presentation.feature.AuthFeatureImpl
import com.google.android.material.textfield.TextInputLayout
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
                .inputNameValue
                .onEach { inputNameLayout.isError(isError = viewModel.inputNameError.value) }
                .launchIn(viewScope)
            viewModel
                .inputNameEnabled
                .onEach(inputName::setEnabled)
                .launchIn(viewScope)

            inputSurname.setText(viewModel.inputSurnameValue.value)
            viewModel
                .inputSurnameValue
                .onEach { inputSurnameLayout.isError(isError = viewModel.inputSurnameError.value) }
                .launchIn(viewScope)
            viewModel
                .inputSurnameEnabled
                .onEach(inputSurname::setEnabled)
                .launchIn(viewScope)

            inputNumber.addTextChangedListener(PhoneTextWatcher())
            inputNumber.setText(viewModel.inputNumberValue.value)
            viewModel
                .inputNumberEnabled
                .onEach(inputNumber::setEnabled)
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

    private fun TextInputLayout.isError(isError: Boolean) {
        error = if (isError) " " else null
        if (childCount == 2) {
            getChildAt(1).isVisible = false
        }
    }

    private inner class PhoneTextWatcher : TextWatcher {

        private var isFormatting = false
        private val code = "+7"
        private val mask = "+7 XXX XXX XX XX"
        private val maskCharacter = 'X'

        init {
            isFormatting = true
            binding.inputNumber.setText(code)
            isFormatting = false
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable) {
            if (isFormatting) {
                return
            }
            binding.inputNumber.removeTextChangedListener(this)

            val unformattedText =
                s.toString().replace(code, "").replace("[^\\d]".toRegex(), "")
            val formattedText = StringBuilder()

            if (unformattedText.isEmpty()) {
                formattedText.append(code)
            } else {
                var maskIndex = 0
                var unformattedIndex = 0

                while (maskIndex < mask.length && unformattedIndex < unformattedText.length) {
                    if (mask[maskIndex] == maskCharacter) {
                        formattedText.append(unformattedText[unformattedIndex])
                        unformattedIndex++
                    } else {
                        formattedText.append(mask[maskIndex])
                    }
                    maskIndex++
                }
            }
            isFormatting = true
            binding.inputNumber.setText(formattedText)
            binding.inputNumber.setSelection(formattedText.length)
            isFormatting = false

            binding.inputNumber.addTextChangedListener(this)
        }
    }
}