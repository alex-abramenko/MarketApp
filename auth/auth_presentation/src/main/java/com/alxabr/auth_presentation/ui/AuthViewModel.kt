package com.alxabr.auth_presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.auth_domain.model.User
import com.alxabr.auth_domain.use_case.AuthLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class DerivedStateFlow<T>(
    private val getValue: () -> T,
    private val flow: Flow<T>
) : StateFlow<T> {

    override val replayCache: List<T>
        get () = listOf(value)

    override val value: T
        get () = getValue()

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        coroutineScope { flow.distinctUntilChanged().stateIn(this).collect(collector) }
    }
}

fun <T1, R> StateFlow<T1>.mapState(transform: (a: T1) -> R): StateFlow<R> {
    return DerivedStateFlow(
        getValue = { transform(this.value) },
        flow = this.map { a -> transform(a) }
    )
}

fun <T1, T2, R> combineStates(flow: StateFlow<T1>, flow2: StateFlow<T2>, transform: (a: T1, b: T2) -> R): StateFlow<R> {
    return DerivedStateFlow(
        getValue = { transform(flow.value, flow2.value) },
        flow = combine(flow, flow2) { a, b -> transform(a, b) }
    )
}

fun StateFlow<Boolean>.consider(stateForConsider: StateFlow<Boolean>): StateFlow<Boolean> =
    combineStates(
        this,
        stateForConsider
    ) { t1, t2 -> t1 && t2 }

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    private val loginUseCase: AuthLoginUseCase
) : ViewModel() {

    private val invalidRegexForString = "[0-9!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex()
    private var isAuthProcess: MutableStateFlow<Boolean> = MutableStateFlow(false)

    // region InputName
    private var _inputNameValue: MutableStateFlow<String> = MutableStateFlow("")
    val inputNameValue: StateFlow<String> by ::_inputNameValue
    val inputNameError: StateFlow<Boolean>
        get() = _inputNameValue.mapState { it.contains(invalidRegexForString) }
    val inputNameEnabled: StateFlow<Boolean>
        get() = isAuthProcess.mapState { !it }
    // endregion

    // region InputSurname
    private var _inputSurnameValue: MutableStateFlow<String> = MutableStateFlow("")
    val inputSurnameValue: StateFlow<String> by ::_inputSurnameValue
    val inputSurnameError: StateFlow<Boolean>
        get() = _inputSurnameValue.mapState { it.contains(invalidRegexForString) }
    val inputSurnameEnabled: StateFlow<Boolean>
        get() = isAuthProcess.mapState { !it }
    // endregion

    // region InputNumber
    private var _inputNumberValue: MutableStateFlow<String> = MutableStateFlow("")
    val inputNumberValue: StateFlow<String> by ::_inputNumberValue
    val inputNumberEnabled: StateFlow<Boolean>
        get() = isAuthProcess.mapState { !it }
    // endregion

    // region ButtonLogin
    val buttonLoginEnabled: StateFlow<Boolean>
        get() =
            isAuthProcess.mapState { it.not() }
                .consider(_inputNameValue.mapState { it.isNotBlank() })
                .consider(_inputSurnameValue.mapState { it.isNotBlank() })
                .consider(_inputNumberValue.mapState { it.length == 16 })
                .consider(inputNameError.mapState { it.not() })
                .consider(inputSurnameError.mapState { it.not() })
    // endregion

    // region Events
    private var _onAuthSuccess: MutableSharedFlow<Unit> = MutableSharedFlow()
    val onAuthSuccess: SharedFlow<Unit> by ::_onAuthSuccess
    // endregion

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
            isAuthProcess.value = true
            if (
                loginUseCase(
                    User(
                        name = _inputNameValue.value,
                        surname = _inputSurnameValue.value,
                        number = _inputNumberValue.value
                    )
                )
            ) {
                _onAuthSuccess.emit(Unit)
            }
            isAuthProcess.value = false
        }
    }

    private fun onNamedChanged(event: AuthUiEvent.OnNameChanged) {
        _inputNameValue.value = event.name
    }

    private fun onSurnameChanged(event: AuthUiEvent.OnSurnameChanged) {
        _inputSurnameValue.value = event.surname
    }

    private fun onNumberChanged(event: AuthUiEvent.OnNumberChanged) {
        _inputNumberValue.value = event.number
    }
}