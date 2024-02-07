package com.alxabr.auth.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> Fragment.findOrCreateViewModelHierarchical(crossinline factory: () -> T): T {
    var parentFragment: Fragment? = parentFragment
    while (parentFragment != null) {
        val viewModel: T? = parentFragment.findViewModel()
        if (viewModel != null) {
            return viewModel
        }
        parentFragment = parentFragment.parentFragment
    }
    return requireActivity().findOrCreateViewModel(factory)
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.findViewModel(): T? =
    try {
        ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    throw IllegalArgumentException("Unexpected view model creation")
            }
        )[T::class.java]
    } catch (e: Throwable) {
        null
    }

inline fun <reified T : ViewModel> ViewModelStoreOwner.findOrCreateViewModel(
    crossinline factory: () -> T
): T =
    ViewModelProvider(
        this,
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return factory() as T
            }
        }
    )[T::class.java]