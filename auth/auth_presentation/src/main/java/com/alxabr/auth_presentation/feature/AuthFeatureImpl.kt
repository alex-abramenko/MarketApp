package com.alxabr.auth_presentation.feature

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.auth_domain.use_case.AuthCheckUseCase
import com.alxabr.auth_presentation.AuthFeature
import com.alxabr.auth_presentation.ui.AuthFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class AuthFeatureFactoryImpl @Inject constructor() : AuthFeature.Factory {

    override fun create(activity: ComponentActivity): AuthFeature =
        activity.viewModels<AuthFeatureImpl>().value
}

@HiltViewModel
class AuthFeatureImpl @Inject constructor(
    private val authCheckUseCase: AuthCheckUseCase
) : AuthFeature, ViewModel() {

    private val _event: MutableSharedFlow<AuthFeature.Event> = MutableSharedFlow()
    override val events: Flow<AuthFeature.Event> by ::_event

    override suspend fun isNeedAuth(): Boolean =
        !authCheckUseCase()

    override fun createAuthFragment(): Fragment =
        AuthFragment()

    fun onAuthSuccess() {
        viewModelScope.launch {
            _event.emit(AuthFeature.Event.AuthSuccess)
        }
    }
}