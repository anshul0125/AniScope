package com.example.aniscope.network

import com.example.aniscope.model.AnimeListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("v4/anime")
    suspend fun getAnimeList(
        @Query("page") page: Int
    ): Response<AnimeListResponse>

    @GET("v4/anime/{animeId}")
    suspend fun getAnimeDetail(
        @Path("animeId") page: Int
    ): Response<AnimeListResponse>

}