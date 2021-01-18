package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import android.app.UiAutomation
import android.os.Build
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-25
 */
class EnableTalkBackTestRule(
    private val enabled: Boolean = true
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)

        if (enabled) {
            ensureAccessibilityServicesUnsuppressed()
            enableTalkBack()
        }
    }

    override fun finished(description: Description) {
        super.finished(description)

        if (enabled) {
            disableTalkBack()
        }
    }


    private fun ensureAccessibilityServicesUnsuppressed() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            // Updates UI Automation so it doesn't suppress accessibility services
            InstrumentationRegistry.getInstrumentation()
                .getUiAutomation(UiAutomation.FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES)
        }
    }

    private fun enableTalkBack() {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand(COMMAND_TALKBACK_ENABLE)
        }
    }

    private fun disableTalkBack() {
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
