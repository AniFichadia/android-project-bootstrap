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
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.anifichadia.bootstrap.testing.ui.testframework.espresso.viewaction.WaitViewAction.Companion.waitFor
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

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

    inline fun <reified ViewT: View> waitForCondition(
        @IdRes id: Int,
        conditionDescription: String? = null,
        crossinline condition: (ViewT) -> Boolean,
    ) = waitForCondition(withId(id), conditionDescription, condition)

    inline fun <reified ViewT : View> waitForCondition(
        viewMatcher: Matcher<View>,
        conditionDescription: String? = null,
        crossinline condition: (ViewT) -> Boolean,
    ) {
        val conditionMatcher = object : BoundedMatcher<View, ViewT>(ViewT::class.java) {
            override fun describeTo(description: Description) {
                conditionDescription?.let(description::appendText)
            }

            override fun matchesSafely(item: ViewT): Boolean = condition(item)
        }

        waitForCondition(viewMatcher, allOf(isDisplayed(), conditionMatcher))
    }

    fun waitForCondition(
        @IdRes id: Int,
        condition: Matcher<View>,
    ) = waitForCondition(withId(id), condition)

    fun waitForCondition(
        viewMatcher: Matcher<View>,
        condition: Matcher<View>,
    ) {
        onView(viewMatcher).perform(waitFor(condition))
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
