package com.example.skripsol.navbar.HomeMenu




import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class FormPengajuan : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_pengajuan_screen)
        val InputLayoutJudulTASebelumnya : TextInputLayout = findViewById(R.id.InputLayout_judul_ta_sebelumnya)
        val EditTextJudulTASebelumnya : EditText = findViewById(R.id.EditText_judul_ta_sebelumnya)

        val InputLayoutJudulTABaru : TextInputLayout = findViewById(R.id.InputLayout_judul_ta_baru)
        val EditTextJudulTABaru : EditText = findViewById(R.id.EditText_judul_ta_baru)

        val InputLayoutAlasanPerubahanJudul : TextInputLayout = findViewById(R.id.InputLayout_alasan_perubahan_judul)
        val EditTextAlasanPerubahanJudul : EditText = findViewById(R.id.EditText_alasan_perubahan_judul)

//      Scroll Function on EditText
        scrollAbleEdittext(EditTextAlasanPerubahanJudul)

        findViewById<ImageView>(R.id.btn_back_form_pengajuan).setOnClickListener {
            Get.back(this)
        }

        findViewById<MaterialButton>(R.id.btn_kirim_form_pengajuan).setOnClickListener {
            Get.dialog(
                this, "Apakah anda yakin", "Ingin mengajukan perubahan Judul TA ?",
                onClickPositive = {

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