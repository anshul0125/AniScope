package com.example.aniscope.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

    fun getInstance(): Retrofit {
        val retrofitClient = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(getHttpLoggingClient())
            .build()

        return retrofitClient
    }

    private fun getHttpLoggingClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val chainRequest = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                return@addInterceptor chain.proceed(chainRequest)
            }
            .callTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .build()
    }
}
