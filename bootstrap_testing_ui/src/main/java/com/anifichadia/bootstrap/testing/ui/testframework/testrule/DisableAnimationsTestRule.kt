package com.anifichadia.bootstrap.testing.ui.testframework.testrule

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestWatcher
import org.junit.runner.Description

private const val animationScaleEnabled: Float = 1f
private const val animationScaleDisabled: Float = 0f

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-21
 */
class DisableAnimationsTestRule(
    private val disableAnimations: Boolean = true
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)

        val disableAnimations = description.retrieveAnnotation(TestAnimation::class.java)?.disableAnimations ?: disableAnimations
        if (disableAnimations) {
            setAnimationScales(animationScaleDisabled)
        }
    }

    override fun finished(description: Description) {
        super.finished(description)

        val disableAnimations = description.retrieveAnnotation(TestAnimation::class.java)?.disableAnimations ?: disableAnimations
        if (disableAnimations) {
            setAnimationScales(animationScaleEnabled)
        }
    }


    fun setAnimationScales(animationScale: Float) {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val packageName = instrumentation.targetContext.packageName

        with(UiDevice.getInstance(instrumentation)) {
            executeShellCommand("pm grant $packageName android.permission.SET_ANIMATION_SCALE")

            executeShellCommand("settings put global transition_animation_scale $animationScale")
            executeShellCommand("settings put global window_animation_scale $animationScale")
            executeShellCommand("settings put global animator_duration_scale $animationScale")
        }
    }


    annotation class TestAnimation(val disableAnimations: Boolean = true)

}
