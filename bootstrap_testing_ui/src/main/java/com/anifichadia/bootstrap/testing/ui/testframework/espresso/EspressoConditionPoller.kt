package com.anifichadia.bootstrap.testing.ui.testframework.espresso

import androidx.test.espresso.NoMatchingRootException
import androidx.test.espresso.NoMatchingViewException

/**
 * This is a blunderbuss, mostly explosion safe Espresso condition poller. This should be used for polling conditions
 * where the screen will not be available yet, as we're waiting for it to be launched.
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
object EspressoConditionPoller {

    private const val POLLING_FREQUENCY_MS: Long = 50L
    const val DEFAULT_TIMEOUT_MS: Long = 5_000L


    fun pollForCondition(timeoutMs: Long = DEFAULT_TIMEOUT_MS, condition: () -> Unit) {
        val timeLimit = System.currentTimeMillis() + timeoutMs
        while (true) {
            try {
                condition()
                return
            } catch (e: Throwable) {
                when (e) {
                    is AssertionError,
                    is NoMatchingViewException,
                    is NoMatchingRootException -> {
                        if (System.currentTimeMillis() > timeLimit) {
                            throw e
                        }
                    }
                    else -> throw e
                }
            }

            Thread.sleep(POLLING_FREQUENCY_MS)
        }
    }
}
