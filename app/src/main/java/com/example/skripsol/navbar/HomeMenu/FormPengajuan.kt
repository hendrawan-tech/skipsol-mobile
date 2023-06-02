package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HeadFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormPengajuan : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_pengajuan_screen)
        val InputLayoutJudulTASebelumnya: TextInputLayout =
            findViewById(R.id.InputLayout_judul_ta_sebelumnya)
        val EditTextJudulTASebelumnya: EditText = findViewById(R.id.EditText_judul_ta_sebelumnya)

        val InputLayoutJudulTABaru: TextInputLayout = findViewById(R.id.InputLayout_judul_ta_baru)
        val EditTextJudulTABaru: EditText = findViewById(R.id.EditText_judul_ta_baru)

        val InputLayoutAlasanPerubahanJudul: TextInputLayout =
            findViewById(R.id.InputLayout_alasan_perubahan_judul)
        val EditTextAlasanPerubahanJudul: EditText =
            findViewById(R.id.EditText_alasan_perubahan_judul)

//      Scroll Function on EditText
        scrollAbleEdittext(EditTextAlasanPerubahanJudul)

        findViewById<ImageView>(R.id.btn_back_form_pengajuan).setOnClickListener {
            Get.back(this)
        }

        findViewById<MaterialButton>(R.id.btn_kirim_form_pengajuan).setOnClickListener {
            Get.dialog(
                this, "Apakah anda yakin", "Ingin mengajukan perubahan Judul TA ?",
                onClickPositive = {
                    val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val token: String? = sharedPreference.getString("token", null)
                    if (token !== null) {
                        Network.instance.addPengjuan(
                            EditTextJudulTASebelumnya.text.toString(),
                            EditTextJudulTABaru.text.toString(),
                            EditTextAlasanPerubahanJudul.text.toString(),
                            token
                        )
                            .enqueue(object : Callback<Map<String, Any>> {
                                @SuppressLint("WrongViewCast")
                                override fun onResponse(
                                    call: Call<Map<String, Any>>,
                                    response: Response<Map<String, Any>>
                                ) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            this@FormPengajuan,
                                            "Berhasil Melakukan Pengajuan",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent =
                                            Intent(this@FormPengajuan, HeadFragment::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this@FormPengajuan,
                                            "Fetch data error",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                                    Toast.makeText(
                                        this@FormPengajuan,
                                        t.message.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    Log.e("asd", t.message.toString())
                                }
                            })
                    } else {
                        val intent = Intent(this@FormPengajuan, Login::class.java)
                        startActivity(intent)
                    }
                },
                onCLickNegative = {

                },

                )
        }
    }

    private fun scrollAbleEdittext(editText: EditText) {
        editText.setOnTouchListener(View.OnTouchListener { v, event ->
            if (editText.hasFocus()) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_SCROLL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                        return@OnTouchListener true
                    }
                }
            }
            false
        })
    }
}