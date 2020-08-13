package com.anifichadia.app.shared

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityManager


/**
 * @author Aniruddh Fichadia
 * @date 2020-08-25
 */
class AccessibilityHelperImpl(
    private val context: Context
) : AccessibilityHelper {

    private val accessibilityManager: AccessibilityManager =
        context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager


    override val accessibilityEnabled: Boolean
        get() = accessibilityManager.isTouchExplorationEnabled

    override val largeTextEnabled: Boolean
        get() = with(context.resources.displayMetrics) { scaledDensity > density }


    override fun focus(view: View) {
        view.clearFocus()
        view.requestFocus()
    }

    override fun announce(view: View, text: CharSequence, interrupt: Boolean) {
        if (interrupt) {
            interrupt()
        }

        view.announceForAccessibility(text)
    }

    override fun interrupt() {
        accessibilityManager.interrupt()
    }
}
