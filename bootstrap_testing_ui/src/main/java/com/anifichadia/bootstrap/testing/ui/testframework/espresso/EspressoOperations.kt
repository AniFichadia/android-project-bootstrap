package com.anifichadia.bootstrap.testing.ui.testframework.espresso

import android.view.View
import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.anifichadia.bootstrap.testing.ui.testframework.espresso.viewaction.WaitViewAction.Companion.waitFor
import org.hamcrest.Matcher

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
object EspressoOperations {

    fun clickView(@IdRes id: Int) = apply {
        onViewAfterScroll(withId(id))
            .perform(click())
    }


    fun waitForViewToBeDisplayed(matcher: Matcher<View>) = apply {
        onViewAfterScroll(matcher)
            .check(matches(isDisplayed()))
    }


    //region Input
    fun inputTypeText(@IdRes id: Int, text: String) = apply {
        onViewAfterScroll(withId(id))
            .perform(replaceText(text))
    }

    fun inputTypeIndividualCharacters(@IdRes id: Int, text: String) = apply {
        onViewAfterScroll(withId(id))
            .perform(typeText(text))

    }

    fun inputClearText(@IdRes id: Int) {
        onViewAfterScroll(withId(id))
            .perform(clearText())
    }
    //endregion


    fun safeScrollToView(matcher: Matcher<View>) = apply {
        try {
            onView(matcher)
                .perform(scrollTo())
        } catch (e: Throwable) {
            // Swallowed
        }

        onView(matcher)
            .perform(waitFor(isDisplayingAtLeast(90)))
    }


    fun onViewAfterScroll(matcher: Matcher<View>): ViewInteraction {
        safeScrollToView(matcher)

        return onView(matcher)
    }
}
