package com.example.aniscope.network

import com.example.aniscope.model.AnimeListResponse
import kotlinx.coroutines.delay
import retrofit2.Response

class Repository {

    val retrofitClient = Retrofit.getInstance().create(ApiInterface::class.java)

    suspend fun getAnimeList(page: Int): Response<AnimeListResponse> {
        return retrofitClient.getAnimeList(page)
    }

    suspend fun getAnimeDetail(id: Int): Response<AnimeListResponse> {
        return retrofitClient.getAnimeDetail(id)
    }

}