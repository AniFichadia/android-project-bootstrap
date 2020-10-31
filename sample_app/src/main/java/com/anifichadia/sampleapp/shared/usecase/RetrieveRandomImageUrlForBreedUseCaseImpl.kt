package com.anifichadia.sampleapp.shared.usecase

import com.anifichadia.sample_sdk.WoofyQuizService
import com.anifichadia.sample_sdk.domain.DogBreed
import com.anifichadia.sampleapp.shared.usecase.RetrieveRandomImageUrlForBreedUseCase.Result
import com.anifichadia.bootstrap.service.ApiResult.Failure
import com.anifichadia.bootstrap.service.ApiResult.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class RetrieveRandomImageUrlForBreedUseCaseImpl(
    private val service: WoofyQuizService
) : RetrieveRandomImageUrlForBreedUseCase {

    override suspend fun execute(input: DogBreed): Result = withContext(Dispatchers.IO) {
        val result = service.retrieveRandomImageUrlForBreed(input.breedName, input.subBreedName)
        when (result) {
            is Success -> Result.Success(result.data)
            is Failure -> Result.Failure
        }
    }
}
