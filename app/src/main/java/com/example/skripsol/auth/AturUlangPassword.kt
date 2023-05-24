package com.example.skripsol.auth


import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.skripsol.R
import com.google.android.material.card.MaterialCardView

class AturUlangPassword :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.atur_ulang_password_screen)
        val card :MaterialCardView = findViewById(R.id.atur_ulang_password_material_card_layout)

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