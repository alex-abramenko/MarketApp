package com.alxabr.auth.presentation

internal sealed interface AuthUiEvent {

    data class OnNameChanged(val name: String) : AuthUiEvent
    data class OnSurnameChanged(val surname: String) : AuthUiEvent
    data class OnNumberChanged(val number: String) : AuthUiEvent

    object OnLoginClick : AuthUiEvent

}