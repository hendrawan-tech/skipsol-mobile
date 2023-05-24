package com.example.skripsol.auth

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class VerifikasiOTP : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verifikasi_otp_screen)
        val card : MaterialCardView = findViewById(R.id.verifikasi_otp_material_card_layout)

        findViewById<MaterialButton>(R.id.btn_verifikasi_otp).setOnClickListener {
            Get.to(this,AturUlangPassword::class.java)
        }

        roundedCardOnlyTop(card)


    }

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


}