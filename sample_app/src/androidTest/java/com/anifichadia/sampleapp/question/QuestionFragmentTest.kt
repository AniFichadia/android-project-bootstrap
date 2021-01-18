package com.anifichadia.sampleapp.question

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anifichadia.bootstrap.testing.ui.testframework.espresso.matcher.RecyclerViewPositionMatcher.Companion.atRecyclerViewPosition
import com.anifichadia.bootstrap.testing.ui.testframework.testrule.DisableAnimationsTestRule
import com.anifichadia.sampleapp.R
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionFragment
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Aniruddh Fichadia
 * @date 2020-08-01
 */
@RunWith(AndroidJUnit4::class)
class QuestionFragmentTest {

    @get:Rule
    val disableAnimationsTestRule = DisableAnimationsTestRule(true)

    @Test
    fun givenConfigProvided_whenUserLaunchesScreen_thenPotentialAnswersShownWithInitialState() {
        launchFragmentInContainer(
            fragmentArgs = QuestionFragment.createArgs(
                listOf("St Bernard", "Corgi", "German Shepherd"),
                "German Shepherd",
                "https://bad.url.com"
            ),
            themeResId = R.style.Theme_Androidprojectbootstrap,
        ) { QuestionFragment() }


        onView(atRecyclerViewPosition(R.id.multiple_choice_question_recyclerview_answers, 0))
            .check(
                matches(
                    allOf(
                        withId(R.id.list_item_multiple_choice_quiz_answer_btn_answer),
                        withText("St Bernard")
                    )
                )
            )

        onView(atRecyclerViewPosition(R.id.multiple_choice_question_recyclerview_answers, 1))
            .check(
                matches(
                    allOf(
                        withId(R.id.list_item_multiple_choice_quiz_answer_btn_answer),
                        withText("Corgi")
                    )
                )
            )

        onView(atRecyclerViewPosition(R.id.multiple_choice_question_recyclerview_answers, 2))
            .check(
                matches(
                    allOf(
                        withId(R.id.list_item_multiple_choice_quiz_answer_btn_answer),
                        withText("German Shepherd")
                    )
                )
            )
    }

}
