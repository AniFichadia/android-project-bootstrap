package com.anifichadia.sampleapp.feature.quizresults

import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import com.anifichadia.sampleapp.screen.quizResultScreen
import com.karumi.shot.ScreenshotTest
import org.junit.Test

/**
 * @author Aniruddh Fichadia
 * @date 2021-05-03
 */
class QuizResultsActivityTest : ScreenshotTest {

    @Test
    fun whenScreenIsLaunched_thenResultsDisplayedCorrectly() {
        launchScreen()

        quizResultScreen {
            assertResultDisplayed(5, 10)
        }
    }


    private fun launchScreen() {
        launchActivity<QuizResultsActivity>(
            intent = QuizResultsActivity.newInstance(
                ApplicationProvider.getApplicationContext(),
                5,
                10,
            )
        )
    }
}
