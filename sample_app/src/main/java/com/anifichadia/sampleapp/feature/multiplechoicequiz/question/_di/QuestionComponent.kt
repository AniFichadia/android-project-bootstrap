package com.anifichadia.sampleapp.feature.multiplechoicequiz.question._di


import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionContract
import com.anifichadia.sampleapp.framework.dependencyinjection.PerActivity
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionFragment
import dagger.Component

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@PerActivity
@Component(
    modules = [
        QuestionModule::class
    ]
)
interface QuestionComponent {

    fun createViewModel(): QuestionContract.ViewModel


    fun inject(to: QuestionFragment)
}
