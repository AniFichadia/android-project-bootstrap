package com.anifichadia.sample_sdk.api

import com.anifichadia.sample_sdk.api.response.ListAllBreedsResponse
import com.anifichadia.sample_sdk.api.response.RandomImageFromABreedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Based off the https://dog.ceo/ API
 *
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
interface DogApi {

    @GET("/api/breeds/list/all")
    fun retrieveAllBreedList(): Call<ListAllBreedsResponse>

    @GET("/api/breed/{breed}/images/random")
    fun retrieveRandomImageForBreed(@Path("breed") breed: String): Call<RandomImageFromABreedResponse>

    @GET("/api/breed/{breed}/{subBreed}/images/random")
    fun retrieveRandomImageForBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): Call<RandomImageFromABreedResponse>
}
