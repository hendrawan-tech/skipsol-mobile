package com.example.skripsol.navbar.HomeMenu

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.FunctionHelper.Get.MBottom
import com.example.skripsol.FunctionHelper.Get.MTop
import com.example.skripsol.FunctionHelper.Get.Miss
import com.example.skripsol.FunctionHelper.Get.True
import com.example.skripsol.FunctionHelper.Get.getMargins
import com.example.skripsol.FunctionHelper.Get.viewStatus
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter.UpdateStatusAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UpdateStatus : AppCompatActivity() {
    private var activeButton: MaterialButton? = null

    private lateinit var button1: MaterialButton
    private lateinit var button2: MaterialButton
    private lateinit var button3: MaterialButton
    private lateinit var button4: MaterialButton
    private lateinit var button5: MaterialButton
    private lateinit var button6: MaterialButton

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UpdateStatusAdapter

    private val itemList = ArrayList<Map<String, Any>>()
    private var statusValue = ""


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_status_screen)
        recyclerView = findViewById(R.id.riwayat_status_recycle_view)

        val ButtonUpdateStatus = findViewById<MaterialButton>(R.id.button_update_status)
        val cardView = findViewById<MaterialCardView>(R.id.card_view)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        getData()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UpdateStatusAdapter(itemList)
        recyclerView.adapter = adapter


        findViewById<ImageView>(R.id.btn_back_update_status).setOnClickListener {
            Get.back(this)
        }


        findViewById<MaterialButton>(R.id.btn_kirim_status).setOnClickListener {
            submit()
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
            handleButtonClick(button1, "Belum Input Judul TA")
        }
        button2.setOnClickListener {
            handleButtonClick(button2, "Sudah Input Judul TA")
        }
        button3.setOnClickListener {
            handleButtonClick(button3, "Sudah Sempro")
        }
        button4.setOnClickListener {
            handleButtonClick(button4, "Sudah Melakukan Sidang")
        }
        button5.setOnClickListener {
            handleButtonClick(button5, "Lulus Dengan Revisi")
        }
        button6.setOnClickListener {
            handleButtonClick(button6, "Belum Lulus")
        }


    }

    private fun onSubmit() {
    }

    private fun submit() {

        var validator = true
        val buttonUpdateStatus = findViewById<MaterialButton>(R.id.button_update_status)
        val btnKirim = findViewById<MaterialButton>(R.id.btn_kirim_status)
        var errUpdateStatus = findViewById<MaterialTextView>(R.id.errUpdateStats)
        if (buttonUpdateStatus.text.equals("Update Status")) {
            errUpdateStatus.text = "*Harap pilih status untuk di Update"
            buttonUpdateStatus.getMargins(MBottom, 5)
            btnKirim.getMargins(MTop, 12)
            errUpdateStatus.viewStatus(True)
            validator = false
        } else {
            buttonUpdateStatus.getMargins(MBottom, 12)
            errUpdateStatus.viewStatus(Miss)

        }
        if (validator) {
            Get.dialog(
                this, "Apakah anda yakin", "Ingin Update Status ?",
                onClickPositive = {
                    val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val token: String? = sharedPreference.getString("token", null)
                    if (token !== null) {
                        Network.instance.addStatus(statusValue, token)
                            .enqueue(object : Callback<Map<String, Any>> {
                                @SuppressLint("WrongViewCast")
                                override fun onResponse(
                                    call: Call<Map<String, Any>>,
                                    response: Response<Map<String, Any>>
                                ) {
                                    if (response.isSuccessful) {
                                        Get.dialogSingle(
                                            this@UpdateStatus,
                                            R.layout.success_dialog,
                                            R.id.successDialogTxt,
                                            R.id.successDialogButton,
                                            "Pengajuan Perubahan status berhasil terikirim!",
                                            singleAction = {
                                                Get.back(this@UpdateStatus)
                                            }
                                        )
                                        Toast.makeText(
                                            this@UpdateStatus,
                                            "Berhasil Update Status",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        itemList.clear()
                                        getData()
                                    } else {
                                        Get.dialogSingle(
                                            this@UpdateStatus,
                                            R.layout.failed_dialog,
                                            R.id.failedDialogTxt,
                                            R.id.failedDialogButton,
                                            "Pengajuan Perubahan status gagal terikirim!",
                                            singleAction = {
                                                Get.back(this@UpdateStatus)
                                            }
                                        )
                                        Toast.makeText(
                                            this@UpdateStatus,
                                            "Perubahan status gagal terikirim!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                                    Toast.makeText(
                                        this@UpdateStatus,
                                        t.message.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    Log.e("Terjadi Kesalahan", t.message.toString())
                                }
                            })
                    } else {
                        Get.offAll(this@UpdateStatus, Login::class.java)
                    }
                },
            )
        }


    }

    private fun getData() {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token: String? = sharedPreference.getString("token", null)
        if (token !== null) {
            Network.instance.getStatus(token).enqueue(object : Callback<Map<String, Any>> {
                @SuppressLint("WrongViewCast")
                override fun onResponse(
                    call: Call<Map<String, Any>>, response: Response<Map<String, Any>>
                ) {
                    val dataResponse = response.body()
                    if (response.isSuccessful && dataResponse != null) {
                        val data = dataResponse["data"] as? List<Map<String, Any>>
                        Log.e("asd", data.toString())
                        if (data != null) {
                            itemList.addAll(data)
                            adapter = UpdateStatusAdapter(itemList)
                            recyclerView.adapter = adapter
                        }
                    } else {
                        Toast.makeText(this@UpdateStatus, "Error fetch data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@UpdateStatus, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            Get.offAll(this@UpdateStatus, Login::class.java)
        }
    }

    private fun handleButtonClick(clickedButton: MaterialButton, status: String) {
        statusValue = status;
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

}



