package com.example.skripsol.config

import retrofit2.Call
import retrofit2.http.*

interface Endpoint {
    @Headers("Accept: application/json")
    @GET("user/profile")
    fun getProfile(@Header("Authorization") token: String): Call<Map<String, Any>>

    @GET("user/dosen-pembimbing")
    fun getChats(@Header("Authorization") token: String, @Query("limit") limit: Int): Call<Map<String, Any>>

    @GET("skripsi/history")
    fun getHistoryJudul(@Header("Authorization") token: String): Call<Map<String, Any>>

    @GET("user/status")
    fun getStatus(@Header("Authorization") token: String): Call<Map<String, Any>>

    @FormUrlEncoded
    @POST("user/status")
    fun addStatus(
        @Field("name") name: String,
        @Header("Authorization") token: String
    ): Call<Map<String, Any>>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Map<String, Any>>
}