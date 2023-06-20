package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
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
import com.example.skripsol.FunctionHelper.Get.MBottom
import com.example.skripsol.FunctionHelper.Get.MTop
import com.example.skripsol.FunctionHelper.Get.Miss
import com.example.skripsol.FunctionHelper.Get.True
import com.example.skripsol.FunctionHelper.Get.getMargins
import com.example.skripsol.FunctionHelper.Get.viewStatus
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HomeMenu.MonitoringAdapter.MonitoringAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Monitoring : AppCompatActivity() {

    private lateinit var MonitoringRecycleView: RecyclerView
    private lateinit var monitoringAdapter: MonitoringAdapter
    private lateinit var progressBarSlider: Slider
    private lateinit var progressBarSliderToText : MaterialTextView



    private val itemList = ArrayList<Map<String, Any>>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.monitoring_screen)

        progressBarSlider = findViewById(R.id.monitoring_progress_slider)
        setGradientColorOnSlider(progressBarSlider)
        val editTextDeskripsiProgress: EditText = findViewById(R.id.EditText_deskripsi_monitoring)

        progressBarSliderToText = findViewById(R.id.monitoring_progress_slider_to_text)
        val btnKirimMonitoring: MaterialButton = findViewById(R.id.btn_kirim_monitoring)
        progressBarSlider.addOnChangeListener { _, value, _ ->
            sliderToText(progressBarSliderToText, progressBarSlider)
        }

        sliderToText(progressBarSliderToText, progressBarSlider)

        MonitoringRecycleView = findViewById(R.id.recylce_riwayat_monitoring)
        monitoringAdapter = MonitoringAdapter(itemList)
        getData() // Fetch monitoring data
        MonitoringRecycleView.layoutManager = LinearLayoutManager(this)
        MonitoringRecycleView.adapter = monitoringAdapter // Set adapter to RecyclerView

        findViewById<ImageView>(R.id.btn_back_monitoring).setOnClickListener {
            Get.back(this)
        }

        btnKirimMonitoring.setOnClickListener {
            onSubmitMonitoring()
        }
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
                        Log.e("Terjadi Kesalahan", data.toString())
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
            Get.offAll(this@Monitoring, Login::class.java)
        }
    }
    private fun sliderToText( intToStringText : MaterialTextView, progressBarSlider : Slider){
        val progressValue = progressBarSlider.value.toInt()
        intToStringText.text = "$progressValue %"
    }
    private fun onSubmitMonitoring(){

        var validator = true
//        Err TextView
        val errProgress = findViewById<MaterialTextView>(R.id.errProgressbar)
        val errDeskripsiMonitoring = findViewById<MaterialTextView>(R.id.errDeskripsiMonitoring)

//        Validation Text
        val progressBarSlider = findViewById<Slider>(R.id.monitoring_progress_slider)
        val editTextDeskripsiProgress : EditText = findViewById(R.id.EditText_deskripsi_monitoring)

//        Layout
        val inputlayoutDeskripsi = findViewById<TextInputLayout>(R.id.InputLayout_deskripsi_monitoring)

        if (progressBarSlider.value.toInt() == 0){
            errProgress.text = "*Progress tidak boleh 0 %"
            errProgress.viewStatus(True)
            errProgress.getMargins(MTop, 5)
            errProgress.getMargins(MBottom, 20)
            validator = false
        }else {
            errProgress.viewStatus(Miss)
        }

        if (editTextDeskripsiProgress.text.isNullOrEmpty()){
            errDeskripsiMonitoring.text = "*Deskripsi monitoring harus diisi"
            errDeskripsiMonitoring.viewStatus(True)
            inputlayoutDeskripsi.getMargins(MBottom, 0)
            errDeskripsiMonitoring.getMargins(MTop, 5)
            errDeskripsiMonitoring.getMargins(MBottom,15)
            validator = false
        }else{
            inputlayoutDeskripsi.getMargins(MBottom,15)
            errDeskripsiMonitoring.viewStatus(Miss)
        }
        if (validator){
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
                                        Get.dialogSingle(
                                            this@Monitoring,
                                            R.layout.success_dialog,
                                            R.id.successDialogTxt,
                                            R.id.successDialogButton,
                                            "Berhasil input Monitoring !",
                                            singleAction = {
                                                Get.back(this@Monitoring)
                                            }
                                        )
                                        Toast.makeText(
                                            this@Monitoring, "Berhasil Update Monitoring", Toast.LENGTH_SHORT
                                        ).show()

                                    } else {
                                        Get.dialogSingle(
                                            this@Monitoring,
                                            R.layout.failed_dialog,
                                            R.id.failedDialogTxt,
                                            R.id.failedDialogButton,
                                            "Gagal input Input Monitoring",
                                            singleAction = {
                                                Get.back(this@Monitoring)
                                            }
                                        )
                                        Toast.makeText(
                                            this@Monitoring, "Terjadi Kesalahan", Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }

                                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                                    Toast.makeText(this@Monitoring, t.message.toString(), Toast.LENGTH_SHORT)
                                        .show()
                                    Log.e("Terjadi Kesalahan", t.message.toString())
                                }
                            })

                    }
                },
            )
        }



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
}