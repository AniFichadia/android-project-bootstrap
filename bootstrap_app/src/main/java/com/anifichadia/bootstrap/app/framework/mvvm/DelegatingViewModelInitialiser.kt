package com.anifichadia.bootstrap.app.framework.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
object DelegatingViewModelInitialiser {

    inline fun <reified ViewModelT : ViewModel> forComponent(
        storeOwner: ViewModelStoreOwner,
        noinline viewModelProvider: () -> ViewModelT
    ): ViewModelT = ViewModelProvider(
        storeOwner,
        DelegatingViewModelFactory(
            viewModelProvider
        )
    ).get(ViewModelT::class.java)


    /**
     * Delegates view model creation to a code block.
     *
     * This is useful to use with dependency injection frameworks, as this cares that a [ViewModel] is created, but not
     * how it's created.
     */
    class DelegatingViewModelFactory<ViewModelT : ViewModel>(
        private val provider: () -> ViewModelT
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return provider.invoke() as T
        }
    }
}
