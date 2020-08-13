package com.anifichadia.app.testframework.testrule

import android.app.UiAutomation
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-25
 */
class EnableTalkBackTestRule(
    private val enabled: Boolean = true
) : TestRule {

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            if (enabled) {
                ensureAccessibilityServicesUnsuppressed()
                enableTalkBack()

                try {
                    base.evaluate()
                } finally {
                    disableTalkBack()
                }
            } else {
                base.evaluate()
            }
        }
    }

    internal fun ensureAccessibilityServicesUnsuppressed() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            // Updates UI Automation so it doesn't suppress accessibility services
            InstrumentationRegistry.getInstrumentation()
                .getUiAutomation(UiAutomation.FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES)
        }
    }

    internal fun enableTalkBack() {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand(COMMAND_TALKBACK_ENABLE)
        }
    }

    internal fun disableTalkBack() {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand(COMMAND_TALKBACK_DISABLE)
        }
    }


    companion object {
        internal const val COMMAND_TALKBACK_ENABLE =
            "settings put secure enabled_accessibility_services com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService"
        internal const val COMMAND_TALKBACK_DISABLE =
            "settings put secure enabled_accessibility_services com,android.talkback/com.google.android.marvin.talkback.TalkBackService"
    }
}
