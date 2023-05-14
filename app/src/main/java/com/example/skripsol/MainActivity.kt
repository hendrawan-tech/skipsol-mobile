package com.example.skripsol

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.config.Network
import com.example.skripsol.model.AuthModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        supportActionBar?.hide()
        login()
    }

    private fun login() {
        Network.instance.login(
            "kminchelle",
            "0lelplRsd",
        ).enqueue(object : Callback<AuthModel> {
            @SuppressLint("WrongViewCast")
            override fun onResponse(call: Call<AuthModel>, response: Response<AuthModel>) {
                val dataResponse = response.body()
                if (response.isSuccessful) {
                    Log.d("API Response", dataResponse.toString())
                } else {
                    // Handle error
                    Log.d("API Response", dataResponse.toString())
                }
            }

            override fun onFailure(call: Call<AuthModel>, t: Throwable) {
                print(t.message)
            }
        })
    }
}