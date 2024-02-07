package com.alxabr.auth_presentation

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface AuthFeature {

    interface Factory {
        fun create(activity: ComponentActivity): AuthFeature
    }

    sealed interface Event {
        object AuthSuccess : Event
    }

    val events: Flow<Event>

    suspend fun isNeedAuth(): Boolean

    fun createAuthFragment(): Fragment
}

/**
 * TODO
 * Отображение ошибок
 * Маска для номера
 * Порефакторить код
 * Дизайн
 */