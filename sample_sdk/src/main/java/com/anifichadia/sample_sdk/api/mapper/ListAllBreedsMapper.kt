package com.anifichadia.sample_sdk.api.mapper

import com.anifichadia.sample_sdk.api.response.ListAllBreedsResponse
import com.anifichadia.sample_sdk.domain.DogBreed
import com.anifichadia.bootstrap.service.retrofit.RetrofitResponseMapper
import retrofit2.Response

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class ListAllBreedsMapper : RetrofitResponseMapper<ListAllBreedsResponse, List<DogBreed>> {

    override fun map(rawResponse: Response<ListAllBreedsResponse>, body: ListAllBreedsResponse?): List<DogBreed> {
        return body!!
            .message
            .entries
            .map {
                val breed = it.key
                val subBreeds = it.value
                if (subBreeds.isNotEmpty()) {
                    mapBreedAndSubBreeds(breed, subBreeds)
                } else {
                    listOf(mapBreed(breed))
                }
            }
            .flatten()
    }

    internal fun mapBreedAndSubBreeds(breed: String, subBreeds: List<String>): List<DogBreed> =
        subBreeds.map { subBreed ->
            DogBreed(
                id = "$breed-$subBreed",
                breedName = breed,
                subBreedName = subBreed
            )
        }

    internal fun mapBreed(breed: String) = DogBreed(
        id = breed,
        breedName = breed
    )
}
