package com.anifichadia.sampleapp.feature.multiplechoicequiz._di

import com.anifichadia.sampleapp.framework.dependencyinjection.AppComponent
import com.anifichadia.sampleapp.framework.dependencyinjection.PerActivity
import com.anifichadia.sampleapp.framework.dependencyinjection.UseCaseModule
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizActivity
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract
import dagger.Component

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@PerActivity
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        MultipleChoiceQuizModule::class,
        UseCaseModule::class
    ]
)
interface MultipleChoiceQuizComponent {

    fun createViewModel(): MultipleChoiceQuizContract.ViewModel


    fun inject(to: MultipleChoiceQuizActivity)
}
