package com.example.skripsol.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Outline
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.skripsol.R
import com.example.skripsol.config.Network
import com.example.skripsol.state.MyState
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
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
        val passwordTextInputLayout : TextInputLayout = findViewById(R.id.InputLayout_login_password)
//        CardView
        val card : MaterialCardView = findViewById(R.id.login_material_card_layout)
        val cornerRadius = 32F

//      Untuk Set Icon Di End Gravity agar dapat berganti-ganti type
        passwordTextInputLayout.setEndIconOnClickListener {
            password.transformationMethod = if (password.transformationMethod == PasswordTransformationMethod.getInstance()) {
                null // Password terlihat, ubah ke tipe input teks biasa
            } else {
                PasswordTransformationMethod.getInstance() // Password tersembunyi, ubah ke tipe input password tersembunyi
            }

            passwordTextInputLayout.endIconDrawable = ContextCompat.getDrawable(this, if (password.transformationMethod == PasswordTransformationMethod.getInstance()) {
                R.drawable.icon_toggle_password_false // Ikon mata terlihat (password tersembunyi)
            } else {
                R.drawable.icon_toggle_password_true // Ikon mata tersembunyi (password terlihat)
            })

            password.setSelection(password.text.length)
        }




//        Fungsi Untuk Rounded Card Di Top nya saja
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            card.outlineProvider = object : ViewOutlineProvider(){
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun getOutline(view: View?, outline: Outline?){
                    outline?.setRoundRect(0,0,view!!.width,(view.height+cornerRadius).toInt(), cornerRadius)
                }
            }
            card.clipToOutline = true
        }





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