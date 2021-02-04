package com.anifichadia.sampleapp.screen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.anifichadia.sampleapp.R
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem

// NOTE! Using an internal constructor so the only way to initialise QuestionScreen is through the method below
class QuestionScreen internal constructor() {

    // NOTE! A screen should expose "interactions" and "assertions"

    // NOTE! Interactions should be named semantically. Don't describe the action (eg. "tap", "click"), describe more of the outcome (eg. user "selects")
    //region Interactions
    fun selectPotentialAnswer(position: Int) {
        clickListItem(R.id.multiple_choice_question_recyclerview_answers, position)
    }
    //endregion


    // NOTE! Assertions prefixed with "assert"
    //region Assertions
    fun assertNumberOfPotentialAnswers(size: Int) {
        assertListItemCount(R.id.multiple_choice_question_recyclerview_answers, size)
    }

    fun assertPotentialAnswerText(position: Int, text: String) {
        assertDisplayedAtPosition(
            R.id.multiple_choice_question_recyclerview_answers,
            position,
            R.id.list_item_multiple_choice_quiz_answer_btn_answer,
            text
        )
    }

    fun assertCorrectAnswerConfirmationShown() {
        // Note: barista doesn't have dialog assertions?
        onView(withId(R.id.alertTitle))
            .inRoot(isDialog())
            .check(matches(withText(R.string.multiple_choice_quiz_answered_correctly)))
    }
    //endregion
}

fun questionScreen(block: QuestionScreen.() -> Unit) {
    QuestionScreen().apply(block)
}
