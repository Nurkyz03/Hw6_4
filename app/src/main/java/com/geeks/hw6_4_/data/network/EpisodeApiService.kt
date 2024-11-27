package com.geeks.hw6_4_.data.network

import com.geeks.hw6_4_.data.model.CharacterResponse
import com.geeks.hw6_4_.data.model.EpisodeResponse
import com.geeks.hw6_4_.data.model.EpisodeResultResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApiService {

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page") page: Int
    ): EpisodeResultResponse

    @GET("episode/{id}")
    suspend fun getSingleEpisode(@Path("id") id: Int): Response<EpisodeResponse>

    @GET("character/{id}")
    suspend fun getEpisodeCharacters(@Path("id") id: Int): Response<CharacterResponse>
}