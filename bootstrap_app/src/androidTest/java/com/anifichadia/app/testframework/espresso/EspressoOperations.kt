package com.anifichadia.app.testframework.espresso

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
import com.anifichadia.app.shared.NoOp
import com.anifichadia.app.testframework.espresso.viewaction.WaitViewAction.Companion.waitFor
import org.hamcrest.Matcher

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-09
 */
object EspressoOperations {

    fun clickView(@IdRes id: Int) = apply {
        doAfterScrollOnInteraction(withId(id)) {
            perform(click())
        }
    }


    fun waitForViewToBeDisplayed(matcher: Matcher<View>) = apply {
        doAfterScrollOnInteraction(matcher) {
            check(matches(isDisplayed()))
        }
    }


    //region Input
    fun inputTypeText(@IdRes id: Int, text: String) = apply {
        doAfterScrollOnInteraction(withId(id)) {
            perform(replaceText(text))
        }
    }

    fun inputTypeIndividualCharacters(@IdRes id: Int, text: String) = apply {
        doAfterScrollOnInteraction(withId(id)) {
            perform(typeText(text))
        }
    }

    fun inputClearText(@IdRes id: Int) {
        doAfterScrollOnInteraction(withId(id)) {
            perform(clearText())
        }
    }
    //endregion


    fun safeScrollToView(matcher: Matcher<View>) = apply {
        try {
            onView(matcher)
                .perform(scrollTo())
        } catch (e: Throwable) {
            NoOp("Swallowed")
        }
    }

    fun doAfterScroll(matcher: Matcher<View>, action: () -> Unit) = apply {
        safeScrollToView(matcher)

        onView(matcher)
            .perform(waitFor(isDisplayingAtLeast(90)))

        action()
    }

    fun doAfterScrollOnInteraction(matcher: Matcher<View>, block: ViewInteraction.() -> Unit) = apply {
        doAfterScroll(matcher) {
            val viewInteraction = onView(matcher)
            block.invoke(viewInteraction)
        }
    }
}
