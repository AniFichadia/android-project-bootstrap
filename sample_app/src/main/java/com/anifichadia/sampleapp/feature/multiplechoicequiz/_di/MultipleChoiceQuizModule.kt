package com.anifichadia.sampleapp.feature.multiplechoicequiz._di

import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizContract
import com.anifichadia.sampleapp.feature.multiplechoicequiz.MultipleChoiceQuizViewModel
import dagger.Module
import dagger.Provides

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@Module
class MultipleChoiceQuizModule {

    @Provides
    fun provideViewModel(viewModel: MultipleChoiceQuizViewModel): MultipleChoiceQuizContract.ViewModel = viewModel
}
