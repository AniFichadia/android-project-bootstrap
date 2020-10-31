package com.anifichadia.sample_sdk.api.mapper

import com.anifichadia.sample_sdk.api.response.RandomImageFromABreedResponse
import com.anifichadia.bootstrap.service.retrofit.RetrofitResponseMapper
import retrofit2.Response


/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
class RandomImageForBreedMapper : RetrofitResponseMapper<RandomImageFromABreedResponse, String> {

    override fun map(
        rawResponse: Response<RandomImageFromABreedResponse>,
        body: RandomImageFromABreedResponse?
    ): String = body!!.message
}
