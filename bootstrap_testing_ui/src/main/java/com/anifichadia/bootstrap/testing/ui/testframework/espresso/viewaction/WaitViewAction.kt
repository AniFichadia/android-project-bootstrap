package com.anifichadia.bootstrap.testing.ui.testframework.espresso.viewaction

import android.view.View
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Matcher

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
class WaitViewAction private constructor(
    private val condition: Matcher<View>,
    private val timeoutMs: Long,
    private val pollFrequencyMs: Long,
) : ViewAction {

    @Suppress("UNCHECKED_CAST")
    override fun getConstraints(): Matcher<View> = anything() as Matcher<View>

    override fun getDescription(): String = "wait"

    override fun perform(uiController: UiController, view: View) {
        uiController.loopMainThreadUntilIdle()

        val endTime = System.currentTimeMillis() + timeoutMs
        while (System.currentTimeMillis() < endTime) {
            if (condition.matches(view)) return

            uiController.loopMainThreadForAtLeast(pollFrequencyMs)
        }

        throw PerformException.Builder()
            .withViewDescription(HumanReadables.describe(view))
            .withActionDescription(description)
            .build()
    }


    companion object {
        const val DEFAULT_POLL_MS: Long = 10L
        const val DEFAULT_TIMEOUT_MS: Long = 5_000L

        fun waitFor(
            condition: Matcher<View>,
            timeout: Long = DEFAULT_TIMEOUT_MS,
            pollFrequencyMs: Long = DEFAULT_POLL_MS
        ) = WaitViewAction(condition, timeout, pollFrequencyMs)
    }
}
