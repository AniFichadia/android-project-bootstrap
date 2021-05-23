package com.anifichadia.sampleapp.screen

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.anifichadia.sampleapp.R
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed

/**
 * @author Aniruddh Fichadia
 * @date 2021-05-17
 */
class QuizResultScreen internal constructor() {
    fun assertResultDisplayed(correctAnswers: Int, questionCount: Int) {
        assertDisplayed(R.id.quiz_results_txt_result,
            ApplicationProvider.getApplicationContext<Context>()
                .getString(R.string.quiz_results_format_result, correctAnswers, questionCount)
        )
    }
}

fun quizResultScreen(block: QuizResultScreen.() -> Unit) {
    QuizResultScreen().apply(block)
}
