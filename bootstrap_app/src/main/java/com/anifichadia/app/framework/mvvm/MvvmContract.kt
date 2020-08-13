package com.anifichadia.app.framework.mvvm

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.ViewModel as AndroidXViewModel

/**
 * A contract that defines user interface components and their (public) interaction contract with each other
 *
 * @author Aniruddh Fichadia
 * @date 2020-08-13
 */
interface MvvmContract {
    /**
     * Basic ViewModel that's designed for usage with kotlin and kotlin coroutines.
     * Running coroutines are automatically cancelled when the [ViewModel] is cleared.
     */
    abstract class ViewModel : AndroidXViewModel(), CoroutineScope {
        private val coroutineSupervisor = SupervisorJob()

        override val coroutineContext: CoroutineContext = Dispatchers.Main.immediate + coroutineSupervisor


        override fun onCleared() {
            super.onCleared()
            coroutineSupervisor.cancel()
        }
    }
}
