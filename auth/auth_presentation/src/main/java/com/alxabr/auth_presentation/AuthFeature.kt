package com.alxabr.auth_presentation

import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.flow.Flow

interface AuthFeature {

    interface Factory {
        fun create(activity: ComponentActivity): AuthFeature
    }

    sealed interface Event {
        object AuthSuccess : Event
    }

    val events: Flow<Event>

    fun startAuthProcess(
        fragmentManager: FragmentManager,
        @IdRes containerId: Int
    )
}

/**
 * TODO
 * Отображение ошибок
 * Маска для номера
 * Порефакторить код
 * Дизайн
 */