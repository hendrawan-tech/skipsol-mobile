package com.example.skripsol.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {
    private const val BASE_URL = "https://8da0-2404-8000-102e-46f5-c4c1-279c-9cf-4335.ngrok-free.app/api/"

    val instance: Endpoint by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Endpoint::class.java)
    }
}