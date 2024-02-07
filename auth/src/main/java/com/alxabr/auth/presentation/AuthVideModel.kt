package com.alxabr.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.auth.domain.model.User
import com.alxabr.auth.domain.use_case.AuthLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthVideModel @Inject constructor(
    private val loginUseCase: AuthLoginUseCase
) : ViewModel() {

    private val invalidRegexForString = "[0-9!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex()

    private var currentName: MutableStateFlow<String> = MutableStateFlow("")
    private var currentSurname: MutableStateFlow<String> = MutableStateFlow("")
    private var currentNumber: MutableStateFlow<String> = MutableStateFlow("")
    private var nameInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var surnameInvalid: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var loginAllowed: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var loading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val initName: String by currentName::value
    val initSurname: String by currentSurname::value
    val initNumber: String by currentNumber::value

    val nameErrorVisible: StateFlow<Boolean> by ::nameInvalid
    val surnameErrorVisible: StateFlow<Boolean> by ::surnameInvalid
    val loginButtonEnabled: StateFlow<Boolean> by ::loginAllowed
    val isLoading: StateFlow<Boolean> by ::loading

    init {
        viewModelScope.launch {
            launch {
                currentName.collect {
                    nameInvalid.value = it.contains(invalidRegexForString)
                }
            }
            launch {
                currentSurname.collect {
                    surnameInvalid.value = it.contains(invalidRegexForString)
                }
            }
            launch {
                combine(currentName, currentSurname) { s1, s2 ->
                    s1.isNotBlank() && s2.isNotBlank()
                }.combine(currentNumber) { bool, s3 ->
                    bool && s3.isNotBlank()
                }.collect {
                    loginAllowed.value = it && !nameInvalid.value && !surnameInvalid.value
                }
            }
        }
    }

    fun onUiEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.OnNameChanged -> onNamedChanged(event)
            is AuthUiEvent.OnSurnameChanged -> onSurnameChanged(event)
            is AuthUiEvent.OnNumberChanged -> onNumberChanged(event)
            AuthUiEvent.OnLoginClick -> onLoginClick()
        }
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            loading.value = true
            delay(3 * 1000)
            loginUseCase(
                User(
                    name = currentName.value,
                    surname = currentSurname.value,
                    number = currentNumber.value
                )
            )
            loading.value = false
        }
    }

    private fun onNamedChanged(event: AuthUiEvent.OnNameChanged) {
        currentName.value = event.name
    }

    private fun onSurnameChanged(event: AuthUiEvent.OnSurnameChanged) {
        currentSurname.value = event.surname
    }

    private fun onNumberChanged(event: AuthUiEvent.OnNumberChanged) {
        currentNumber.value = event.number
    }
}