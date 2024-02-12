package com.alxabr.auth_presentation.feature

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alxabr.auth_domain.model.User
import com.alxabr.auth_domain.use_case.AuthGetUserUseCase
import com.alxabr.auth_domain.use_case.AuthLoginUseCase
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
    private val getUserUseCase: AuthGetUserUseCase,
    private val loginUseCase: AuthLoginUseCase
) : AuthFeature, ViewModel() {

    companion object {
        private const val AUTH_FRAGMENT_TAG = "AuthFeatureImpl.AUTH_FRAGMENT_TAG"
    }

    private val _event: MutableSharedFlow<AuthFeature.Event> = MutableSharedFlow()
    override val events: Flow<AuthFeature.Event> by ::_event

    override fun startAuthProcess(fragmentManager: FragmentManager, containerId: Int) {
        if (fragmentManager.findFragmentByTag(AUTH_FRAGMENT_TAG) == null) {
            viewModelScope.launch {
                val user: User? = getUserUseCase()
                if (user != null) {
                    if (loginUseCase(user = user)) {
                        onAuthSuccess()
                    } else {
                        showAuthFragment(fragmentManager = fragmentManager, containerId = containerId)
                    }
                } else {
                    showAuthFragment(fragmentManager = fragmentManager, containerId = containerId)
                }
            }
        }
    }

    private fun showAuthFragment(fragmentManager: FragmentManager, containerId: Int) {
        fragmentManager
            .beginTransaction()
            .replace(containerId, AuthFragment(), AUTH_FRAGMENT_TAG)
            .commitAllowingStateLoss()
    }

    fun onAuthSuccess() {
        viewModelScope.launch {
            _event.emit(AuthFeature.Event.AuthSuccess)
        }
    }
}