package com.anifichadia.sampleapp.feature.multiplechoicequiz.question._di

import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionContract
import com.anifichadia.sampleapp.feature.multiplechoicequiz.question.QuestionViewModel
import dagger.Module
import dagger.Provides

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@Module
class QuestionModule {
    @Provides
    fun provideViewModel(viewModel: QuestionViewModel): QuestionContract.ViewModel = viewModel
}
