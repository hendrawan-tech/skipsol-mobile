package com.example.skripsol

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HeadFragment
import com.example.skripsol.state.MyState
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
                        MyState.setDataUser(dataResponse?.get("data")?.let { it as? Map<*, *> }?.get("user") as Map<String, Any>);
                        val intent = Intent(this@MainActivity, HeadFragment::class.java)
                        startActivity(intent)
                    } else {
                        val intent = Intent(this@MainActivity, Login::class.java)
                        startActivity(intent)
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}