package com.anifichadia.sampleapp.screen

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.anifichadia.bootstrap.testing.ui.testframework.espresso.EspressoOperations.onViewAfterScroll
import com.anifichadia.bootstrap.testing.ui.testframework.espresso.matcher.RecyclerViewPositionMatcher.Companion.atRecyclerViewPosition
import com.anifichadia.bootstrap.testing.ui.testframework.espresso.matcher.RecyclerViewSizeMatcher.Companion.recyclerViewWithSize
import com.anifichadia.sampleapp.R
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf

// NOTE! Using an internal constructor so the only way to initialise QuestionScreen is through the method below
class QuestionScreen internal constructor() {

    // NOTE! A screen should expose "interactions" and "assertions"

    // NOTE! Interactions should be named semantically. Don't describe the action (eg. "tap", "click"), describe more of the outcome (eg. user "selects")
    //region Interactions
    fun selectPotentialAnswer(position: Int) {
        onViewAfterScroll(potentialAnswer(position))
            .perform(click())
    }
    //endregion


    // NOTE! Assertions prefixed with "assert"
    //region Assertions
    fun assertNumberOfPotentialAnswers(size: Int) {
        onView(potentialAnswersList())
            .check(matches(recyclerViewWithSize(size)))
    }

    fun assertPotentialAnswerText(position: Int, text: String) {
        onViewAfterScroll(potentialAnswer(position))
            .check(matches(withText(text)))
    }

    fun assertCorrectAnswerConfirmationShown() {
        onView(withId(R.id.alertTitle))
            .inRoot(isDialog())
            .check(matches(withText(R.string.multiple_choice_quiz_answered_correctly)))
    }
    //endregion


    private companion object {
        fun potentialAnswer(position: Int): Matcher<View> =
            allOf(
                atRecyclerViewPosition(R.id.multiple_choice_question_recyclerview_answers, position),
                withId(R.id.list_item_multiple_choice_quiz_answer_btn_answer),
            )

        fun potentialAnswersList(): Matcher<View> = withId(R.id.multiple_choice_question_recyclerview_answers)
    }
}

fun questionScreen(block: QuestionScreen.() -> Unit) {
    QuestionScreen().apply(block)
}
