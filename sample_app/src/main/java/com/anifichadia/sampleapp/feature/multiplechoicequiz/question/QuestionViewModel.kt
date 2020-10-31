package com.anifichadia.sampleapp.feature.multiplechoicequiz.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionContract
import javax.inject.Inject

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class QuestionViewModel @Inject constructor() : QuestionContract.ViewModel() {

    override lateinit var potentialAnswers: List<String>
    override lateinit var answer: String
    override lateinit var answerImageUrl: String

    private val _userAnswer = MutableLiveData<QuestionContract.UserAnswer>()
    override val userAnswer: LiveData<QuestionContract.UserAnswer> = _userAnswer


    override fun onUserSubmittedAnswer(answerIndex: Int) {
        if (_userAnswer.value == null) {
            // Evaluate the users answer
            val correct = potentialAnswers.indexOf(answer) == answerIndex
            _userAnswer.value = QuestionContract.UserAnswer(answerIndex, correct)
        }
    }
}
