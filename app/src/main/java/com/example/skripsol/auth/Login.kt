package com.example.skripsol.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skripsol.R
import com.example.skripsol.config.Network
import com.example.skripsol.model.AuthModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
//        login()
    }

    private fun login() {
        Network.instance.login(
            "kminchelle",
            "0lelplR",
        ).enqueue(object : Callback<AuthModel> {
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                print(response.code())
                print(response.body())
            }

            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                print(t.message)
            }
        })
    }
}