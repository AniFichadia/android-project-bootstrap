package com.anifichadia.sampleapp.feature.multiplechoicequiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anifichadia.bootstrap.app.framework.mvvm.Event
import com.anifichadia.bootstrap.app.framework.mvvm.MvvmContract


/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface MultipleChoiceQuizContract : MvvmContract {

    abstract class ViewModel : MvvmContract.ViewModel() {
        abstract val loadingStatus: LiveData<LoadingStatus>
        abstract val quizStatus: LiveData<QuizStatus>
        abstract val quizResult: MutableLiveData<Event<QuizResult>>

        abstract fun generateQuizIfRequired()

        abstract fun onQuestionAnsweredCorrectly()
        abstract fun onQuestionAnsweredIncorrectly()
    }

    data class QuizConfig(
        val questionCount: Int = 10,
        val potentialAnswerCount: Int = 4
    )

    enum class LoadingStatus { LOADING, SUCCESS, FAILURE }

    data class Question(
        val potentialAnswers: List<String>,
        val answer: String,
        val answerImageUrl: String
    )

    data class QuizStatus(
        val allQuestions: List<Question>,
        val currentQuestionNumber: Int,
        val correctAnswerCount: Int = 0
    ) {
        val questionCount: Int = allQuestions.size
        val currentQuestion: Question = allQuestions[currentQuestionNumber - 1]
    }

    data class QuizResult(
        val correctAnswerCount: Int,
        val questionCount: Int
    )
}
