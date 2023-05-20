package com.example.skripsol.navbar.HomeMenu


import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R


class InputJudulTA : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_judul_ta_screen)


        findViewById<ImageView>(R.id.btn_back_input_judul_ta).setOnClickListener {
           Get.back(this)
        }


    }
}