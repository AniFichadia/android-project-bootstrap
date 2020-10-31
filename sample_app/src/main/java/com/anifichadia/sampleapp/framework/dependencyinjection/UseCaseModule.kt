package com.anifichadia.sampleapp.framework.dependencyinjection


import com.anifichadia.sample_sdk.WoofyQuizService
import com.anifichadia.sampleapp.shared.usecase.RetrieveAllBreedsUseCase
import com.anifichadia.sampleapp.shared.usecase.RetrieveAllBreedsUseCaseImpl
import com.anifichadia.sampleapp.shared.usecase.RetrieveRandomImageUrlForBreedUseCase
import com.anifichadia.sampleapp.shared.usecase.RetrieveRandomImageUrlForBreedUseCaseImpl
import dagger.Module
import dagger.Provides

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
@Module
class UseCaseModule {

    @Provides
    fun provideRetrieveAllBreedsUseCase(
        service: WoofyQuizService
    ): RetrieveAllBreedsUseCase = RetrieveAllBreedsUseCaseImpl(service)

    @Provides
    fun provideRetrieveRandomImageUrlForBreedUseCase(
        service: WoofyQuizService
    ): RetrieveRandomImageUrlForBreedUseCase = RetrieveRandomImageUrlForBreedUseCaseImpl(service)
}
