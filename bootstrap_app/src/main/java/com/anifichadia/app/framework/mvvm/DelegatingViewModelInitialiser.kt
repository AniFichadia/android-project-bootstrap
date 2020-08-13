package com.anifichadia.app.framework.mvvm

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
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


    @Deprecated("Use forComponent() instead", replaceWith = ReplaceWith("forComponent"))
    inline fun <reified ViewModelT : ViewModel> forActivity(
        activity: ComponentActivity,
        noinline viewModelProvider: () -> ViewModelT
    ): ViewModelT = ViewModelProvider(
        activity,
        DelegatingViewModelFactory(
            viewModelProvider
        )
    ).get(ViewModelT::class.java)

    @Deprecated("Use forComponent() instead", replaceWith = ReplaceWith("forComponent"))
    inline fun <reified ViewModelT : ViewModel> forFragment(
        fragment: Fragment,
        noinline viewModelProvider: () -> ViewModelT
    ): ViewModelT = ViewModelProvider(
        fragment,
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
