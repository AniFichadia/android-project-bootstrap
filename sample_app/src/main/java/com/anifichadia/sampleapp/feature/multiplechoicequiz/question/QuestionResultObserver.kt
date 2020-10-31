package com.anifichadia.sampleapp.feature.multiplechoicequiz.question

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface QuestionResultObserver {
    fun onQuestionAnsweredCorrectly()
    fun onQuestionAnsweredIncorrectly()
}
