package com.anifichadia.app.shared

import android.view.View

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-25
 */
interface AccessibilityHelper {
    val accessibilityEnabled: Boolean
    val largeTextEnabled: Boolean

    fun focus(view: View)
    fun announce(view: View, text: CharSequence, interrupt: Boolean = true)
    fun interrupt()
}
