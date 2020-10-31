package com.anifichadia.sample_sdk.api.response

import com.google.gson.annotations.SerializedName

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
data class ListAllBreedsResponse(
    @SerializedName("status") val status: String,
    /** The key is the breed of the dog, and the value is a list of sub-breeds */
    @SerializedName("message") val message: Map<String, List<String>>
)
