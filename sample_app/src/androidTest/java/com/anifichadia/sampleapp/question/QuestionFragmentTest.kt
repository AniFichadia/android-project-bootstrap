package com.anifichadia.sampleapp.question

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anifichadia.bootstrap.testing.ui.testframework.testrule.DisableAnimationsTestRule
import com.anifichadia.sampleapp.R
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionFragment
import com.anifichadia.sampleapp.screen.questionScreen
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
        launchScreen()

        questionScreen {
            assertNumberOfPotentialAnswers(3)
            assertPotentialAnswerText(0, "St Bernard")
            assertPotentialAnswerText(1, "Corgi")
            assertPotentialAnswerText(2, "German Shepherd")
        }
    }

    @Test
    fun whenUserSelectsCorrectAnswer_thenCorrectAnswerConfirmationShown() {
        launchScreen()

        questionScreen {
            selectPotentialAnswer(2)

            assertCorrectAnswerConfirmationShown()
        }
    }


    private fun launchScreen() {
        launchFragmentInContainer(
            fragmentArgs = QuestionFragment.createArgs(
                listOf("St Bernard", "Corgi", "German Shepherd"),
                "German Shepherd",
                "https://bad.url.com"
            ),
            themeResId = R.style.Theme_Androidprojectbootstrap,
        ) { QuestionFragment() }
    }
}
