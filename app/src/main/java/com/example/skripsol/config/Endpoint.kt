package com.example.skripsol.config

import retrofit2.Call
import retrofit2.http.*

interface Endpoint {
    @Headers("Accept: application/json")
    @GET("user/profile")
    fun getProfile(@Header("Authorization") token: String): Call<Map<String, Any>>

    @GET("user/dosen-pembimbing")
    fun getChats(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int
    ): Call<Map<String, Any>>

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
    @POST("skripsi/input-ta")
    fun addSkripsi(
        @Field("judul_1") judul1: String,
        @Field("judul_2") judul2: String,
        @Field("deskripsi_1") deskripsi1: String,
        @Field("deskripsi_2") deskripsi2: String,
        @Field("output_1") output1: String,
        @Field("output_2") output2: String,
        @Field("pembimbing_1") pembimbing1: String,
        @Field("pembimbing_2") pembimbing2: String,
        @Header("Authorization") token: String
    ): Call<Map<String, Any>>

    @FormUrlEncoded
    @POST("skripsi/pengajuan")
    fun addPengjuan(
        @Field("judul_sebelum") judul_sebelum: String,
        @Field("judul_sesudah") judul_sesudah: String,
        @Field("alasan") alasan: String,
        @Header("Authorization") token: String
    ): Call<Map<String, Any>>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Map<String, Any>>

    @FormUrlEncoded
    @POST("skripsi/monitoring")
    fun updateProgress(
        @Field("progress")progress: Int,
        @Field("deskripsi") deskripsi : String,
        @Header("Authorization") token: String
    ):Call<Map<String, Any>>


}