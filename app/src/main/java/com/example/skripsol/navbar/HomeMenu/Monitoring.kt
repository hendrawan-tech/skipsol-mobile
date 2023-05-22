package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.navbar.HomeMenu.MonitoringAdapter.MonitoringAdapter
import com.example.skripsol.navbar.HomeMenu.MonitoringAdapter.MonitoringData
import com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter.UpdateStatusAdapter
import com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter.UpdateStatusData
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider
import com.google.android.material.textview.MaterialTextView

class Monitoring : AppCompatActivity() {

    private lateinit var MonitoringRecycleView: RecyclerView
    private lateinit var monitoringAdapter: MonitoringAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.monitoring_screen)

        val progressBarSlider = findViewById<Slider>(R.id.monitoring_progress_slider)
        setGradientColorOnSlider(progressBarSlider)

        val progressBarSliderToText = findViewById<MaterialTextView>(R.id.monitoring_progress_slider_to_text)

        progressBarSlider.addOnChangeListener { _, value, _ ->
            sliderToText(progressBarSliderToText,progressBarSlider)
        }
        sliderToText(progressBarSliderToText,progressBarSlider)

        MonitoringRecycleView = findViewById(R.id.recylce_riwayat_monitoring)
        monitoringAdapter = MonitoringAdapter(generateRandomData())

        MonitoringRecycleView.layoutManager = LinearLayoutManager(this)
        MonitoringRecycleView.adapter = monitoringAdapter


        findViewById<ImageView>(R.id.btn_back_monitoring).setOnClickListener {
            Get.back(this)
        }


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




}