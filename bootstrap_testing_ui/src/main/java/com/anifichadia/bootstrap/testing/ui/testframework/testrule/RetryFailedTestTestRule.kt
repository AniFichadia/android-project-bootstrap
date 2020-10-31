package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import org.junit.internal.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-21
 */
class RetryFailedTestTestRule(
    private val enabled: Boolean = true,
    private val defaultMaxAttemptCount: Int = 3
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            if (enabled) {
                val testName = description.testName()

                val retryAnnotation = description.retrieveAnnotation(Retry::class.java)
                val maxAttemptCount = retryAnnotation?.maxAttemptCount ?: defaultMaxAttemptCount

                val failureReasons = mutableListOf<Throwable>()
                for (attemptNumber in 1..maxAttemptCount) {
                    try {
                        base.evaluate()
                    } catch (e: AssumptionViolatedException) {
                        // Test has been skipped, just propagate this exception as this is understood by the test runner
                        throw e
                    } catch (e: Throwable) {
                        failureReasons += e
                        if (attemptNumber < maxAttemptCount) {
                            println("$testName failed. Attempt $attemptNumber of $maxAttemptCount. Caused by $e")
                        } else {
                            throw RepeatedFailureException(testName, maxAttemptCount, failureReasons)
                        }
                    }
                }
            } else {
                base.evaluate()
            }
        }
    }

    class RepeatedFailureException(
        val testName: String,
        val maxAttemptCount: Int,
        val failureReasons: List<Throwable>
    ) : Exception(
        "$testName failed after $maxAttemptCount attempts. Caused by ${formatFailureReasons(failureReasons)}",
        failureReasons.last()
    ) {
        private companion object {
            fun formatFailureReasons(failureReasons: List<Throwable>): String {
                return failureReasons
                    .mapIndexed { index, throwable -> "${index + 1} - $throwable" }
                    .joinToString("\n\t")
            }
        }
    }

    annotation class Retry(val maxAttemptCount: Int)
}
