package com.anifichadia.sample_sdk

import com.anifichadia.sample_sdk.api.DogApi
import com.anifichadia.sample_sdk.api.mapper.ListAllBreedsMapper
import com.anifichadia.sample_sdk.api.mapper.RandomImageForBreedMapper
import com.anifichadia.sample_sdk.api.response.ListAllBreedsResponse
import com.anifichadia.sample_sdk.api.response.RandomImageFromABreedResponse
import com.anifichadia.sample_sdk.domain.DogBreed
import com.anifichadia.bootstrap.service.ApiResult
import com.anifichadia.bootstrap.service.ServiceConfiguration
import com.anifichadia.bootstrap.service.retrofit.RetrofitCallWrapper
import retrofit2.Retrofit

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class WoofyQuizServiceImpl(
    private val serviceConfiguration: ServiceConfiguration,
    private val retrofit: Retrofit,
) : WoofyQuizService {

    private val dogApi: DogApi by lazy { retrofit.create(DogApi::class.java) }


    override suspend fun retrieveAllBreeds(): ApiResult<List<DogBreed>, Unit> {
        val call = dogApi.retrieveAllBreedList()
        val mapper = ListAllBreedsMapper()

        return RetrofitCallWrapper<ListAllBreedsResponse, List<DogBreed>, Unit, Unit>(retrofit, mapper)
            .execute(call)
    }

    override suspend fun retrieveRandomImageUrlForBreed(breed: String, subBreed: String?): ApiResult<String, Unit> {
        val call = if (subBreed == null) {
            dogApi.retrieveRandomImageForBreed(breed)
        } else {
            dogApi.retrieveRandomImageForBreed(breed, subBreed)
        }
        val mapper = RandomImageForBreedMapper()

        return RetrofitCallWrapper<RandomImageFromABreedResponse, String, Unit, Unit>(retrofit, mapper)
            .execute(call)
    }
}
