package com.example.skripsol.config

import com.example.skripsol.model.AuthModel
import com.example.skripsol.model.TestModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {
    @GET("test")
    fun getData(): Call<ArrayList<TestModel>>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<AuthModel>
}