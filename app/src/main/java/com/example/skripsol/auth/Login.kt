package com.example.skripsol.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.skripsol.R
import com.example.skripsol.config.Network
import com.example.skripsol.state.MyState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        supportActionBar?.hide()
        val button: AppCompatButton = findViewById(R.id.btn_login)
        val email: EditText = findViewById(R.id.EditText_login_nim)
        val password: EditText = findViewById(R.id.EditText_login_password)

        button.setOnClickListener {
            login(email.text.toString(), password.text.toString())
        }
    }

    private fun login(email: String, password: String) {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val button: AppCompatButton = findViewById(R.id.btn_login)
        button.text = "Loading . . ."
        button.isEnabled = false
        Network.instance.login(email, password)
            .enqueue(object : Callback<Map<String, Any>> {
                @SuppressLint("WrongViewCast")
                override fun onResponse(
                    call: Call<Map<String, Any>>, response: Response<Map<String, Any>>
                ) {
                    button.text = "Login"
                    button.isEnabled = true
                    val dataResponse = response.body()
                    if (response.isSuccessful) {
                        val editor: SharedPreferences.Editor = sharedPreference.edit()
                        editor.putString(
                            "token",
                            "Bearer " + dataResponse?.get("data")?.let { it as? Map<String, Any> }
                                ?.get("token")
                                .toString()
                        )
                        editor.apply()
                        MyState.setDataUser(dataResponse?.get("data")?.let { it as? Map<String, Any> }?.get("user") as Map<String, Any>);

                    } else {
                        Toast.makeText(this@Login, "Fetch data error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    button.text = "Login"
                    button.isEnabled = true
                    Toast.makeText(this@Login, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}