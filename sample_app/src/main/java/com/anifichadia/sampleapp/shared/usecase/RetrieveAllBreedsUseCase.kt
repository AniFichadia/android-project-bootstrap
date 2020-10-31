package com.anifichadia.sampleapp.shared.usecase

import com.anifichadia.bootstrap.app.framework.UseCase
import com.anifichadia.sample_sdk.domain.DogBreed
import com.anifichadia.sampleapp.shared.usecase.RetrieveAllBreedsUseCase.Result

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface RetrieveAllBreedsUseCase : UseCase<Unit, Result> {

    sealed class Result {
        class Success(val output: List<DogBreed>) : Result()
        object Failure : Result()
    }
}
