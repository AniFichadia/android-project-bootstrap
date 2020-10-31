package com.anifichadia.sampleapp.shared.usecase

import com.anifichadia.bootstrap.app.framework.UseCase
import com.anifichadia.sample_sdk.domain.DogBreed

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface RetrieveRandomImageUrlForBreedUseCase : UseCase<DogBreed, RetrieveRandomImageUrlForBreedUseCase.Result> {

    sealed class Result {
        class Success(val output: String) : Result()
        object Failure : Result()
    }
}
