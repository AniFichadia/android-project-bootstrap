package com.anifichadia.sampleapp.feature.multiplechoicequiz.question

import androidx.lifecycle.LiveData
import com.anifichadia.bootstrap.app.framework.mvvm.MvvmContract

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface QuestionContract : MvvmContract {

    interface Navigation : MvvmContract.Navigation {
        fun navigateOnCorrectAnswer()

        fun navigateOnIncorrectAnswer()
    }

    abstract class ViewModel : MvvmContract.ViewModel() {
        abstract var potentialAnswers: List<String>
        abstract var answer: String
        abstract var answerImageUrl: String

        abstract val userAnswer: LiveData<UserAnswer>

        abstract fun onUserSubmittedAnswer(answerIndex: Int)
    }

    data class UserAnswer(val answerIndex: Int, val correct: Boolean)
}
