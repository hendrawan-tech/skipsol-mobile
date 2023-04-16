package com.example.skripsol.config

import com.example.skripsol.model.TestModel
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {
    @GET("test")
    fun getData(): Call<ArrayList<TestModel>>
}