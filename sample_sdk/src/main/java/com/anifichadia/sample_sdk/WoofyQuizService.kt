package com.anifichadia.sample_sdk

import com.anifichadia.sample_sdk.domain.DogBreed
import com.anifichadia.bootstrap.service.ApiResult

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface WoofyQuizService {

    suspend fun retrieveAllBreeds(): ApiResult<List<DogBreed>, Unit>

    suspend fun retrieveRandomImageUrlForBreed(breed: String, subBreed: String? = null): ApiResult<String, Unit>
}
