package com.example.skripsol.auth

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HeadFragment
import com.example.skripsol.state.MyState
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)


        supportActionBar?.hide()
        val button: AppCompatButton = findViewById(R.id.btn_login)
        val buttonLupaPassword : MaterialTextView = findViewById(R.id.btn_lupa_password)
        val email: EditText = findViewById(R.id.EditText_login_nim)
        val password: EditText = findViewById(R.id.EditText_login_password)
        val passwordTextInputLayout: TextInputLayout = findViewById(R.id.InputLayout_login_password)
//        CardView
        val card: MaterialCardView = findViewById(R.id.login_material_card_layout)

//        passwordIconAnimation(passwordTextInputLayout,password)
        roundedCardOnlyTop(card)

        buttonLupaPassword.setOnClickListener {
            Get.to(this, LupaPassword::class.java)
        }

        button.setOnClickListener {
            login(email.text.toString().trim(), password.text.toString().trim())
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
                    Log.e("asd", dataResponse.toString())
                    if (response.isSuccessful) {
                        val editor: SharedPreferences.Editor = sharedPreference.edit()
                        editor.putString(
                            "token",
                            "Bearer " + dataResponse?.get("data")?.let { it as? Map<String, Any> }
                                ?.get("token")
                                .toString()
                        )
                        editor.apply()
                        MyState.setDataUser(
                            dataResponse?.get("data")?.let { it as? Map<String, Any> }
                                ?.get("user") as Map<String, Any>
                        );

                        Get.offAll(this@Login, HeadFragment::class.java)
                    } else {
                        Toast.makeText(
                            this@Login, "Fetch data error",
                            Toast.LENGTH_SHORT
                        ).show()
                        val txtErrorPassword: MaterialTextView = findViewById(R.id.error_passwordNotif)
                        val txtErrorNim : MaterialTextView = findViewById(R.id.error_nimNotif)
                        txtErrorNim.setTextColor(Color.RED)
                        txtErrorNim.visibility = View.VISIBLE
                        txtErrorPassword.setTextColor(Color.RED)
                        txtErrorPassword.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    button.text = "Login"
                    button.isEnabled = true
                    Toast.makeText(this@Login, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

    //        Fungsi Untuk Rounded Card Di Top nya saja
    private fun roundedCardOnlyTop(cardView: CardView) {
        val cornerRadius = 32F
        val shapeDrawable = ShapeDrawable().apply {
            shape = RoundRectShape(
                floatArrayOf(
                    cornerRadius, cornerRadius,
                    cornerRadius, cornerRadius,
                    0F, 0F,
                    0F, 0F
                ),
                null,
                null
            )

            paint.color = Color.WHITE
        }
        cardView.background = shapeDrawable
    }


    //    fungsi Untuk mengubah icon mata pada password
//    private fun passwordIconAnimation(passwordTextInputLayout : TextInputLayout , passwordEditText: EditText){
//        passwordTextInputLayout.setOnClickListener {
//            val isPasswordVisible =
//                passwordEditText.inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//
//            if (isPasswordVisible) {
//                // Password is currently visible, hide it
//                passwordEditText.inputType =
//                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//                passwordTextInputLayout.setEndIconDrawable(R.drawable.icon_toggle_password_false)
//            } else {
//                // Password is currently hidden, show it
//                passwordEditText.inputType =
//                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                passwordTextInputLayout.setEndIconDrawable(R.drawable.icon_toggle_password_true)
//            }
//        }
//
//    }
}