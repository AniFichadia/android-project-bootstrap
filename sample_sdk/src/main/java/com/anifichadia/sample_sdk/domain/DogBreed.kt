package com.anifichadia.sample_sdk.domain

/**
 * @author Aniruddh Fichadia
 * @date 2020-10-18
 */
data class DogBreed(
    val id: String,
    val breedName: String,
    val subBreedName: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DogBreed

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id.hashCode()
}
