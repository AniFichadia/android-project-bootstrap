package com.anifichadia.sample_sdk.api.response

import com.google.gson.annotations.SerializedName

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
data class RandomImageFromABreedResponse(
    @SerializedName("status") val status: String,
    /** The message is just the URL */
    @SerializedName("message") val message: String
)
