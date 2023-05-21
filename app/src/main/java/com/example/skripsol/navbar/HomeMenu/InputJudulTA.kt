package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.google.android.material.textfield.TextInputLayout


class InputJudulTA : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_judul_ta_screen)

        val textInputJudulTA :TextInputLayout = findViewById(R.id.InputLayout_input_judul_ta)
        val editTextJudulTA : EditText = findViewById(R.id.EditText_input_judul_ta)

        val inputLayoutAbstrakTA: TextInputLayout = findViewById(R.id.InputLayout_abstrak_ta)
        val editTextAbstrakTA: EditText = findViewById(R.id.EditText_abstrak_ta)

        


        scrollAbleEdittext(editTextAbstrakTA)
        findViewById<ImageView>(R.id.btn_back_input_judul_ta).setOnClickListener {
            Get.back(this)
        }




    }



    @SuppressLint("ClickableViewAccessibility")
    private fun scrollAbleEdittext(editText : EditText){
        editText.setOnTouchListener(OnTouchListener { v, event ->
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