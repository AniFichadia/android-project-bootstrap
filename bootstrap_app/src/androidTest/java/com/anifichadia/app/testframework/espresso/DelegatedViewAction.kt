package com.anifichadia.app.testframework.espresso

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
open class DelegatedViewAction(
    val delegate: ViewAction
) : ViewAction {

    override fun getConstraints(): Matcher<View> = delegate.constraints

    override fun getDescription(): String = delegate.description

    override fun perform(uiController: UiController, view: View) = delegate.perform(uiController, view)
}
