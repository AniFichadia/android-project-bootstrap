package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import androidx.test.espresso.intent.Intents
import com.anifichadia.bootstrap.testing.ui.testframework.testrule.IntentsTestRule.ChecksIntents
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * If you're using espresso's [Intents] class, this helps manage initing and releasing it in a guaranteed way.
 *
 * To use this, annotate your test with [ChecksIntents]
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
class IntentsTestRule : TestRule {

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            val annotation = description.retrieveAnnotation(ChecksIntents::class.java)
            val requiresIntents = annotation?.enabled ?: false

            if (requiresIntents) {
                wrap {
                    base.evaluate()
                }
            } else {
                base.evaluate()
            }
        }
    }


    annotation class ChecksIntents(val enabled: Boolean = true)


    companion object {
        fun wrap(block: () -> Unit) {
            Intents.init()
            try {
                block()
            } finally {
                Intents.release()
            }
        }
    }
}
