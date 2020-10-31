package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-21
 */
class DisableAnimationsTestRule(
    private val enabled: Boolean = true
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            if (enabled) {
                setAnimationScales(animationScaleDisabled)
                try {
                    base.evaluate()
                } finally {
                    setAnimationScales(animationScaleEnabled)
                }
            } else {
                base.evaluate()
            }
        }
    }

    private fun setAnimationScales(animationScale: Float) {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand("settings put global transition_animation_scale $animationScale")
            executeShellCommand("settings put global window_animation_scale $animationScale")
            executeShellCommand("settings put global animator_duration_scale $animationScale")
        }
    }


    private companion object {
        const val animationScaleEnabled: Float = 1f
        const val animationScaleDisabled: Float = 0f
    }
}
