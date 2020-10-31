package com.anifichadia.sampleapp.shared.usecase

import com.anifichadia.sample_sdk.WoofyQuizService
import com.anifichadia.bootstrap.service.ApiResult.Failure
import com.anifichadia.bootstrap.service.ApiResult.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class RetrieveAllBreedsUseCaseImpl(
    private val service: WoofyQuizService
) : RetrieveAllBreedsUseCase {

    override suspend fun execute(input: Unit): RetrieveAllBreedsUseCase.Result = withContext(Dispatchers.IO) {
        val result = service.retrieveAllBreeds()
        when (result) {
            is Success -> RetrieveAllBreedsUseCase.Result.Success(result.data)
            is Failure -> RetrieveAllBreedsUseCase.Result.Failure
        }
    }
}
