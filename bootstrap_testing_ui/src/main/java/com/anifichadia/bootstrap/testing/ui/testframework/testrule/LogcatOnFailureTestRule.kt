package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import org.junit.internal.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.TimeUnit

/**
 * Based off: https://www.braze.com/perspectives/article/logcat-junit-android-tests
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-26
 */
class LogcatOnFailureTestRule(
    private val enabled: Boolean = true
) : TestRule {

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            if (enabled) {
                val testName = description.toString()
                val testStartMessage = "TestRunner: started: $testName"
                val testEndMessage = "TestRunner: finished: $testName"
                val testFailedMessage = "TestRunner: failed: $testName"

                clearLogcat()

                try {
                    base.evaluate()
                } catch (e: AssumptionViolatedException) {
                    // Test has been skipped, just propagate this exception as this is understood by the test runner
                    throw e
                } catch (e: Throwable) {
                    val logcatOutput = retrieveLogcatOutput()
                    if (logcatOutput != null) {
                        val logcatOutputLines = logcatOutput.split("\n")
                        // TODO if required: .filter { it.contains(android.os.Process.myPid().toString() }

                        val startLineIndex = logcatOutputLines
                            .indexOfLast { it.contains(testStartMessage) }
                            .takeIf { it >= 0 }
                            ?: 0
                        val endLineIndex = logcatOutputLines
                            .indexOfLast { it.contains(testEndMessage) || it.contains(testFailedMessage) }
                            .takeIf { it >= 0 }
                            ?: logcatOutputLines.size - 1

                        val testOutputLines = logcatOutputLines.subList(startLineIndex, endLineIndex).joinToString("\n")

                        clearLogcat()

                        throw Exception(
                            """
========== logcat output ==========
$testOutputLines
=================================== 
                        """.trimIndent(),
                            e
                        )
                    } else {
                        throw e
                    }
                }
            } else {
                base.evaluate()
            }
        }
    }


    private fun clearLogcat() {
        runProcess(COMMAND_LOGCAT_CLEAR)
    }

    private fun retrieveLogcatOutput(): String? {
        val (exitCode, output) = runProcess(COMMAND_LOGCAT_EXPORT, 5L)
        return if (exitCode == 0) output else null
    }

    private fun runProcess(command: String, timeoutMs: Long? = null): Pair<Int, String> {
        val process = Runtime.getRuntime().exec(command)
        val processExitCode = if (VERSION.SDK_INT >= VERSION_CODES.O && timeoutMs != null) {
            try {
                process.waitFor(timeoutMs, TimeUnit.MILLISECONDS)
                process.exitValue()
            } catch (e: InterruptedException) {
                -1
            }
        } else {
            process.waitFor()
        }

        val processOutput = if (processExitCode == 0) process.inputStream else process.errorStream
        val output = processOutput.bufferedReader().use { it.readText() }

        return processExitCode to output
    }


    private companion object {
        const val COMMAND_LOGCAT_CLEAR = "logcat -c"
        const val COMMAND_LOGCAT_EXPORT = "logcat -d -v threadtime"
    }
}
