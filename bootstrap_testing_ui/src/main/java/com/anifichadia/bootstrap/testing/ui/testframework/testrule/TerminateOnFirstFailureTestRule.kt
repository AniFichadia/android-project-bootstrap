package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import org.junit.internal.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-21
 */
class TerminateOnFirstFailureTestRule(
    private val enabled: Boolean = true
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            if (enabled) {
                val testName = description.testName()
                if (failedTestName == null) {
                    try {
                        base.evaluate()
                    } catch (e: AssumptionViolatedException) {
                        // Test has been skipped, just propagate this exception as this is understood by the test runner
                        throw e
                    } catch (e: Throwable) {
                        failedTestName = testName
                        throw e
                    }
                } else {
                    throw AssumptionViolatedException("$failedTestName failed. Skipping subsequent test: $testName")
                }
            } else {
                base.evaluate()
            }
        }
    }


    private companion object {
        // TODO: need something to store this instead
        var failedTestName: String? = null
    }
}
