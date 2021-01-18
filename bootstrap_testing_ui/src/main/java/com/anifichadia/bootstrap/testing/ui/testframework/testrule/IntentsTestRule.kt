package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import androidx.test.espresso.intent.Intents
import com.anifichadia.bootstrap.testing.ui.testframework.testrule.IntentsTestRule.ChecksIntents
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * If you're using espresso's [Intents] class, this helps manage initing and releasing it in a guaranteed way.
 *
 * To use this, annotate your test with [ChecksIntents]
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
class IntentsTestRule : TestWatcher() {

    private var requiresIntents: Boolean = false

    override fun starting(description: Description) {
        super.starting(description)

        val annotation = description.retrieveAnnotation(ChecksIntents::class.java)
        requiresIntents = annotation?.enabled ?: false

        if (requiresIntents) {
            Intents.init()
        }
    }

    override fun finished(description: Description) {
        super.finished(description)

        if (requiresIntents) {
            Intents.release()
        }
    }

    annotation class ChecksIntents(val enabled: Boolean = true)
}
