package com.example.skripsol

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        setContentView(R.layout.splash_screen)
        supportActionBar?.hide()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getData() {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token: String? = sharedPreference.getString("token", null)
        if (token !== null) {
            Network.instance.getProfile(token).enqueue(object : Callback<Map<String, Any>> {
                @SuppressLint("WrongViewCast")
                override fun onResponse(call: Call<Map<String, Any>>, response: Response<Map<String, Any>>) {
                    val dataResponse = response.body()
                    if (response.isSuccessful) {
                        Log.d("API Response", dataResponse.toString())
                    } else {
                        Log.d("API Response", dataResponse.toString())
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    print(t.message)
                }
            })
        } else {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}