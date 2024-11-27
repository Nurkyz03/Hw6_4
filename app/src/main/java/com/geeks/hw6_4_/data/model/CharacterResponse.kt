package com.geeks.hw6_4_.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResultsResponse(
    @SerializedName("info")
    val pageInfo: PageInfo?,
    @SerializedName("results")
    val characterResponse: List<CharacterResponse>?
)

data class CharacterResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: Location
)

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val uri: String
)