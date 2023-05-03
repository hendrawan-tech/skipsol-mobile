package com.example.skripsol.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.skripsol.R
import com.example.skripsol.navbar.HeadFragment

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val ten = Intent(this,HeadFragment::class.java)
            startActivity(ten)
            finish()    
         }
    }
}