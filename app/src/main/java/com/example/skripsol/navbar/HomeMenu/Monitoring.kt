package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HeadFragment
import com.example.skripsol.navbar.HomeMenu.MonitoringAdapter.MonitoringAdapter
import com.example.skripsol.navbar.HomeMenu.MonitoringAdapter.MonitoringData
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Monitoring : AppCompatActivity() {

    private lateinit var MonitoringRecycleView: RecyclerView
    private lateinit var monitoringAdapter: MonitoringAdapter

    private val itemList = ArrayList<Map<String, Any>>()
    private var statusValue = ""
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.monitoring_screen)

        val progressBarSlider = findViewById<Slider>(R.id.monitoring_progress_slider)
        setGradientColorOnSlider(progressBarSlider)
        val editTextDeskripsiProgress : EditText = findViewById(R.id.EditText_deskripsi_monitoring)

        val progressBarSliderToText = findViewById<MaterialTextView>(R.id.monitoring_progress_slider_to_text)
        val btnKirimMonitoring : MaterialButton = findViewById(R.id.btn_kirim_monitoring)
        progressBarSlider.addOnChangeListener { _, value, _ ->
            sliderToText(progressBarSliderToText,progressBarSlider)
        }

        sliderToText(progressBarSliderToText,progressBarSlider)

        MonitoringRecycleView = findViewById(R.id.recylce_riwayat_monitoring)
//        monitoringAdapter = MonitoringAdapter(generateRandomData())
        getData()
        MonitoringRecycleView.layoutManager = LinearLayoutManager(this)
//        MonitoringRecycleView.adapter = monitoringAdapter


        findViewById<ImageView>(R.id.btn_back_monitoring).setOnClickListener {
            Get.back(this)
        }

        btnKirimMonitoring.setOnClickListener {
            Get.dialog(
                this, "Apakah anda yakin", "Ingin Update Monitoring ?",
                onClickPositive = {
                    val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val token: String? = sharedPreference.getString("token", null)
                    if (token !== null) {
                        Network.instance.updateProgress(progressBarSlider.value.toInt(), editTextDeskripsiProgress.text.toString(),token)
                            .enqueue(object : Callback<Map<String, Any>> {

                                override fun onResponse(
                                    call: Call<Map<String, Any>>, response: Response<Map<String, Any>>
                                ) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            this@Monitoring, "Berhasil Update Status", Toast.LENGTH_SHORT
                                        ).show()
                                        Get.offAll(this@Monitoring, HeadFragment::class.java)
                                    } else {
                                        Toast.makeText(
                                            this@Monitoring, "Fetch data error", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                                    Toast.makeText(this@Monitoring, t.message.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                    Log.e("asd", t.message.toString())
                                }
                            })

                    }
                },
                onCLickNegative = {

                },

                )
        }




    }

    private fun postProgress(progress: Int, deskripsi:String) {

    }
    private fun generateRandomData(): List<MonitoringData> {


        val randomData = mutableListOf<MonitoringData>()

        repeat(10) {
            val broadcastTitle = arrayOf(
                "Pertemuan Awal: Diskusi mengenai topik skripsi",
                "Pertemuan Literatur: Riset literatur terkait skripsi",
                "Pertemuan Metodologi: Pembahasan metodologi penelitian",
                "Pertemuan Analisis: Analisis data skripsi",
                "Pertemuan Penulisan: Penulisan bab-bab skripsi",
                "Pertemuan Evaluasi: Evaluasi hasil penelitian",
                "Pertemuan Presentasi: Persiapan presentasi skripsi",
                "Pertemuan Revisi: Revisi skripsi berdasarkan masukan dosen",
                "Pertemuan Final: Persiapan sidang skripsi",
                "Pertemuan Sidang: Melakukan sidang skripsi"
                // Tambahkan judul pertemuan lainnya sesuai dengan kebutuhan
            ).random()

            val hari = (1..31).random()
            val bulan = (1..12).random()
            val tahun = (2018..2023).random()
            val tanggal = "$hari- $bulan - $tahun"


            randomData.add(
                MonitoringData(
                    R.drawable.img_list_riwayat_monitoring,
                    broadcastTitle,
                    tanggal,
                )
            )
        }
        return randomData


    }

   private fun setGradientColorOnSlider(slider: Slider){
        val startColor = Color.parseColor("#FF246BFD")
        val endColor = Color.parseColor("#FF6497FF")
        val colorStateList = ColorStateList.valueOf(startColor)

        // Mengatur efek gradient menggunakan ColorStateList pada track color active
        slider.trackActiveTintList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(-android.R.attr.state_enabled)
            ),
            intArrayOf(startColor, endColor)
        )

        // Mengatur thumb color menjadi transparan
        slider.thumbTintList = ColorStateList.valueOf(Color.TRANSPARENT)
    }

    private fun sliderToText( intToStringText : MaterialTextView, progressBarSlider : Slider){
        val progressValue = progressBarSlider.value.toInt()
        intToStringText.text = "$progressValue %"
    }

    private fun getData() {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token: String? = sharedPreference.getString("token", null)
        if (token !== null) {
            Network.instance.getHistoryMonitoring(token).enqueue(object : Callback<Map<String, Any>> {
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
                            monitoringAdapter= MonitoringAdapter(itemList)
                            MonitoringRecycleView.adapter = monitoringAdapter
                        }
                    } else {
                        Toast.makeText(this@Monitoring, "Error fetch data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@Monitoring, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            val intent = Intent(this@Monitoring, Login::class.java)
            startActivity(intent)
        }
    }






}