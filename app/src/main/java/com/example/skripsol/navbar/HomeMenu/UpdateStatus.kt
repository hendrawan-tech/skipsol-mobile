package com.example.skripsol.navbar.HomeMenu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter.UpdateStatusAdapter
import com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter.UpdateStatusData
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView


class UpdateStatus : AppCompatActivity() {
    private var activeButton: MaterialButton? = null

    private lateinit var button1: MaterialButton
    private lateinit var button2: MaterialButton
    private lateinit var button3: MaterialButton
    private lateinit var button4: MaterialButton
    private lateinit var button5: MaterialButton
    private lateinit var button6: MaterialButton

    private lateinit var UpdateStatusRecycleView: RecyclerView

    private lateinit var updateStatusAdapter: UpdateStatusAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_status_screen)


        val ButtonUpdateStatus = findViewById<MaterialButton>(R.id.button_update_status)
        val cardView = findViewById<MaterialCardView>(R.id.card_view)


        UpdateStatusRecycleView = findViewById(R.id.riwayat_status_recycle_view)
        updateStatusAdapter = UpdateStatusAdapter(generateRandomData())

        UpdateStatusRecycleView.layoutManager = LinearLayoutManager(this)
        UpdateStatusRecycleView.adapter = updateStatusAdapter


      findViewById<ImageView>(R.id.btn_back_update_status).setOnClickListener {

            Get.back(this)


        }



        ButtonUpdateStatus.setOnClickListener {
            if (cardView.visibility == View.VISIBLE) {
                val fadeOutAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 1f, 0f)
                fadeOutAnimator.duration = 250
                fadeOutAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        cardView.visibility = View.GONE
                        ButtonUpdateStatus.icon =
                            ContextCompat.getDrawable(this@UpdateStatus, R.drawable.icon_arrow_up)

                        // Setelah animasi selesai, baru mengatur ikon dan menjalankan animasi masuk
                        cardView.alpha = 0f
                        val fadeInAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 0f, 1f)
                        fadeInAnimator.duration = 250
                        fadeInAnimator.start()
                    }
                })
                fadeOutAnimator.start()
            } else {
                cardView.visibility = View.VISIBLE
                ButtonUpdateStatus.icon =
                    ContextCompat.getDrawable(this, R.drawable.icon_arrow_down)

                // Mengatur posisi awal dan menjalankan animasi masuk
                cardView.alpha = 0f
                val fadeInAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 0f, 1f)
                fadeInAnimator.duration = 250
                fadeInAnimator.start()
            }
        }

        button1 = findViewById(R.id.btn_belum_input_judul_ta)
        button2 = findViewById(R.id.btn_sudah_input_judul_ta)
        button3 = findViewById(R.id.btn_sudah_sempro)
        button4 = findViewById(R.id.btn_sudah_melakukan_sidang)
        button5 = findViewById(R.id.btn_lulus_dengan_revisi)
        button6 = findViewById(R.id.btn_belum_lulus)


        button1.setOnClickListener {
            handleButtonClick(button1)
        }
        button2.setOnClickListener {
            handleButtonClick(button2)
        }
        button3.setOnClickListener {
            handleButtonClick(button3)
        }
        button4.setOnClickListener {
            handleButtonClick(button4)
        }
        button5.setOnClickListener {
            handleButtonClick(button5)
        }
        button6.setOnClickListener {
            handleButtonClick(button6)
        }


    }


    private fun handleButtonClick(clickedButton: MaterialButton) {
        val ButtonUpdateStatus = findViewById<MaterialButton>(R.id.button_update_status)
        val cardView = findViewById<MaterialCardView>(R.id.card_view)
        activeButton?.apply {
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
            icon = null
        }

        clickedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.secondary_variant))
        clickedButton.icon = ContextCompat.getDrawable(this, R.drawable.icon_checked_update_status)
        clickedButton.setTextColor(ContextCompat.getColor(this, R.color.primary_color))
        activeButton = clickedButton
        ButtonUpdateStatus.text = clickedButton.text
        ButtonUpdateStatus.icon = ContextCompat.getDrawable(this, R.drawable.icon_arrow_up)

//        Fungsi Untuk Visible dan Gone Card dengan konsep DropDown Button
        if (cardView.visibility == View.VISIBLE) {
            val fadeOutAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 1f, 0f)
            fadeOutAnimator.duration = 250
            fadeOutAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    cardView.visibility = View.GONE
                    ButtonUpdateStatus.icon =
                        ContextCompat.getDrawable(this@UpdateStatus, R.drawable.icon_arrow_up)

                    // Setelah animasi selesai, baru mengatur ikon dan menjalankan animasi masuk
                    cardView.alpha = 0f
                    val fadeInAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 0f, 1f)
                    fadeInAnimator.duration = 250
                    fadeInAnimator.start()
                }
            })
            fadeOutAnimator.start()
        } else {
            cardView.visibility = View.VISIBLE
            ButtonUpdateStatus.icon = ContextCompat.getDrawable(this, R.drawable.icon_arrow_down)

            // Mengatur posisi awal dan menjalankan animasi masuk
            cardView.alpha = 0f
            val fadeInAnimator = ObjectAnimator.ofFloat(cardView, "alpha", 0f, 1f)
            fadeInAnimator.duration = 250
            fadeInAnimator.start()
        }

    }


   private  fun generateRandomData(): List<UpdateStatusData> {


        val randomData = mutableListOf<UpdateStatusData>()

        repeat(10) {
            val updateStatus = arrayOf(
                "Belum input judul TA",
                "Sudah input judul TA",
                "Sudah Sempro",
                "Sudah melakukan sidang",
                "Lulus dengan revisi",
                "Belum Lulus"
            ).random()
            val verify = arrayOf(true, false).random()
            val hari = (1..31).random()
            val bulan = (1..12).random()
            val tahun = (2018..2023).random()
            val tanggal = "$hari- $bulan - $tahun"


            randomData.add(
                UpdateStatusData(
                    R.drawable.img_model_profile,
                    updateStatus,
                    tanggal,
                    verify
                )
            )
        }
        return randomData


    }


}



