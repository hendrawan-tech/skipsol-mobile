package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.FunctionHelper.Get.getMargins
import com.example.skripsol.FunctionHelper.Get.viewStatus
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
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
            onSubmitPengajuan()
        }
    }
    private fun onSubmitPengajuan() {
        var validator = true

        val InputLayoutJudulTABaru: TextInputLayout = findViewById(R.id.InputLayout_judul_ta_baru)
        val editTextJudulTASebelumnya: EditText = findViewById(R.id.EditText_judul_ta_sebelumnya)
        val editTextJudulTABaru: EditText = findViewById(R.id.EditText_judul_ta_baru)
        val editTextAlasanPerubahanJudul: EditText =
            findViewById(R.id.EditText_alasan_perubahan_judul)

        var errJudulTaSebelumnya : MaterialTextView = findViewById(R.id.errJudulTaSebelumnya)
        var errJudulTaBaru : MaterialTextView = findViewById(R.id.errJudulTaBaru)
        var errAlasanPerubahanJudul: MaterialTextView = findViewById(R.id.errAlasanPerubahanJudul)
        if (editTextJudulTASebelumnya.text.isNullOrEmpty()){
            errJudulTaSebelumnya.text = "*Harap isi Judul TA Sebelumnya"
            errJudulTaSebelumnya.viewStatus(Get.True)
            errJudulTaSebelumnya.getMargins(Get.MTop,5)
            validator = false
        }else{
            errJudulTaSebelumnya.viewStatus(Get.Miss)
        }
        if (editTextJudulTABaru.text.isNullOrEmpty()){
            errJudulTaBaru.text = "*Harap isi Judul TA Baru"
            errJudulTaBaru.viewStatus(Get.True)
            errJudulTaBaru.getMargins(Get.MTop, 5)
            errJudulTaBaru.getMargins(Get.MBottom, 15)
            InputLayoutJudulTABaru.getMargins(Get.MBottom,0)
            validator = false
        }else{
            errJudulTaBaru.viewStatus(Get.Miss)
            InputLayoutJudulTABaru.getMargins(Get.MBottom,15)
        }
        if (editTextAlasanPerubahanJudul.text.isNullOrEmpty()){
            errAlasanPerubahanJudul.text = "*Harap isi Alasan Perubahan Judul TA"
            errAlasanPerubahanJudul.viewStatus(Get.True)
            errAlasanPerubahanJudul.getMargins(Get.MTop,5)
            validator = false

        }else{
            errAlasanPerubahanJudul.viewStatus(Get.Miss)
        }
        if (validator){
            Get.dialog(
                this, "Apakah anda yakin", "Ingin mengajukan perubahan Judul TA ?",
                onClickPositive = {
                    val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val token: String? = sharedPreference.getString("token", null)
                    if (token !== null) {
                        Network.instance.addPengjuan(
                            editTextJudulTASebelumnya.text.toString(),
                            editTextJudulTABaru.text.toString(),
                            editTextAlasanPerubahanJudul.text.toString(),
                            token
                        )
                            .enqueue(object : Callback<Map<String, Any>> {
                                @SuppressLint("WrongViewCast")
                                override fun onResponse(
                                    call: Call<Map<String, Any>>,
                                    response: Response<Map<String, Any>>
                                ) {
                                    if (response.isSuccessful) {
                                        Get.dialogSingle(
                                            this@FormPengajuan,
                                            R.layout.success_dialog,
                                            R.id.successDialogTxt,
                                            R.id.successDialogButton,
                                            "Pengajuan Perubahan Judul berhasil terikirim!",
                                            singleAction = {
                                                Get.back(this@FormPengajuan)
                                            }
                                        )
                                        Toast.makeText(
                                            this@FormPengajuan, "Pengajuan Perubahan Judul berhasil terikirim!", Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Get.dialogSingle(
                                            this@FormPengajuan,
                                            R.layout.failed_dialog,
                                            R.id.failedDialogTxt,
                                            R.id.failedDialogButton,
                                            "Pengiriman Pengajuan Perubahan Judul gagal terikirim!",
                                            singleAction = {
                                                Get.back(this@FormPengajuan)
                                            }
                                        )
                                        Toast.makeText(
                                            this@FormPengajuan, "Terjadi Kesalahan", Toast.LENGTH_SHORT
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
                        Get.offAll(this@FormPengajuan, Login::class.java)
                    }
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