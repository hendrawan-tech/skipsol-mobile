package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
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
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InputJudulTA : AppCompatActivity() {

    private val itemList = ArrayList<Map<String, Any>>()
    private val selectedDosenList = mutableListOf<Map<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_judul_ta_screen)
        getData()

        val deskripsi1: EditText = findViewById(R.id.EditText_deskripsi_1)
        val deskripsi2: EditText = findViewById(R.id.EditText_deskripsi_2)
        val output1: EditText = findViewById(R.id.EditText_output_1)
        val output2: EditText = findViewById(R.id.EditText_output_2)
        val btnKirimInputJudulTA: MaterialButton = findViewById(R.id.btn_kirim_input_judul_ta)

        btnKirimInputJudulTA.setOnClickListener {
            onSubmitTA()
        }
        setupDropDownPilihDosenPembimbing()
        scrollAbleEdittext(deskripsi1)
        scrollAbleEdittext(deskripsi2)
        scrollAbleEdittext(output1)
        scrollAbleEdittext(output2)
        findViewById<ImageView>(R.id.btn_back_input_judul_ta).setOnClickListener {
            Get.back(this)
        }
    }

    private fun onSubmitTA(
    ) {

        var validator = true
//        err Text String
        var errJudulPertamaTA = ""
        var errDeskripsiPertama = ""
        var errOutputPertama = ""
        var errJudulKeduaTA = ""
        var errDeskripsiKedua = ""
        var errOutputKedua = ""
        var errDosenPembimbing1 = ""
        var errDosenPembimbing2 = ""

//        InputLayout TextLayout
        val inputJudul1 = findViewById<TextInputLayout>(R.id.InputLayout_input_judul_ta)
        val inputDeskripsi1 = findViewById<TextInputLayout>(R.id.InputLayout_deskripsi_1)
        val inputOutput1 = findViewById<TextInputLayout>(R.id.InputLayout_output_1)
        val inputJudul2 = findViewById<TextInputLayout>(R.id.InputLayout_input_judul_ta2)
        val inputDeskripsi2 = findViewById<TextInputLayout>(R.id.InputLayout_deskripsi_2)
        val inputOutput2 = findViewById<TextInputLayout>(R.id.InputLayout_output_2)
        val inputDospem1 = findViewById<TextInputLayout>(R.id.InputLayout_dospem1)
        val inputDospem2 = findViewById<TextInputLayout>(R.id.InputLayout_dospem2)

//        Input EditText
        val textInputJudulTA1: TextInputEditText = findViewById(R.id.EditText_input_judul_ta)
        val textInputJudulTA2: TextInputEditText = findViewById(R.id.EditText_input_judul_ta2)
        val deskripsi1: EditText = findViewById(R.id.EditText_deskripsi_1)
        val deskripsi2: EditText = findViewById(R.id.EditText_deskripsi_2)
        val output1: EditText = findViewById(R.id.EditText_output_1)
        val output2: EditText = findViewById(R.id.EditText_output_2)
        val pilihDosenPembimbing1: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing1)
        val pilihDosenPembimbing2: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing2)

//        err TextView
        val viewTxtJudulPertama = findViewById<MaterialTextView>(R.id.errJudulPertama)
        val viewTxtDeskripsiPertama = findViewById<MaterialTextView>(R.id.errDeskripsiPertama)
        val viewTxtOutputPertama = findViewById<MaterialTextView>(R.id.errOutputPertama)
        val viewTxtJudulKedua = findViewById<MaterialTextView>(R.id.errJudulKedua)
        val viewTxtDeskripsiKedua = findViewById<MaterialTextView>(R.id.errDeskripsiKedua)
        val viewTxtOutputKedua = findViewById<MaterialTextView>(R.id.errOutputKedua)
        val viewTxtPilihDospem1 = findViewById<MaterialTextView>(R.id.errPilihDosenPembimbing1)
        val viewTxtPilihDospem2 = findViewById<MaterialTextView>(R.id.errPilihDosenPembimbing2)

        if (textInputJudulTA1.text.isNullOrEmpty()) {
            errJudulPertamaTA = "*Judul Pertama TA harus diisi"
            viewTxtJudulPertama.text = errJudulPertamaTA
            viewTxtJudulPertama.viewStatus(True)
            inputJudul1.getMargins(MBottom,0)
            viewTxtJudulPertama.getMargins(MTop,5)
            viewTxtJudulPertama.getMargins(MBottom,15)
            validator = false
        } else {
            inputJudul1.getMargins(MBottom,20)
            viewTxtJudulPertama.viewStatus(Miss)
        }
        if (deskripsi1.text.isNullOrEmpty()) {
            errDeskripsiPertama = "*Deskripsi TA harus diisi"
            viewTxtDeskripsiPertama.text = errDeskripsiPertama
            viewTxtDeskripsiPertama.viewStatus(True)
            inputDeskripsi1.getMargins(MBottom, 0)
            viewTxtDeskripsiPertama.getMargins(MTop,5)
            viewTxtDeskripsiPertama.getMargins(MBottom,15)
            validator = false
        } else {
            viewTxtDeskripsiPertama.viewStatus(Miss)
            inputDeskripsi1.getMargins(MBottom, 20)
        }
        if (output1.text.isNullOrEmpty()) {
            errOutputPertama = "*Output TA harus diisi"
            viewTxtOutputPertama.text = errOutputPertama
            viewTxtOutputPertama.viewStatus(True)
            inputOutput1.getMargins(MBottom,0)
            viewTxtOutputPertama.getMargins(MTop,5)
            viewTxtOutputPertama.getMargins(MBottom,15)
            validator = false
        } else {
            viewTxtOutputPertama.viewStatus(Miss)
            output1.getMargins(MBottom, 20)
        }
        if (textInputJudulTA2.text.isNullOrEmpty()) {
            errJudulKeduaTA = "*Judul Kedua TA harus diisi"
            viewTxtJudulKedua.text = errJudulKeduaTA
            viewTxtJudulKedua.viewStatus(True)
            inputJudul2.getMargins(MBottom,0)
            viewTxtJudulKedua.getMargins(MTop,5)
            viewTxtJudulKedua.getMargins(MBottom,15)
            validator = false
        } else {
            viewTxtJudulKedua.viewStatus(Miss)
            inputJudul2.getMargins(MBottom,20)
        }
        if (deskripsi2.text.isNullOrEmpty()) {
            errDeskripsiKedua = "*Deskripsi kedua TA harus diisi"
            viewTxtDeskripsiKedua.text = errDeskripsiKedua
            viewTxtDeskripsiKedua.viewStatus(True)
            inputDeskripsi2.getMargins(MBottom,0)
            viewTxtDeskripsiKedua.getMargins(MTop,5)
            viewTxtDeskripsiKedua.getMargins(MBottom,15)
            validator = false
        } else {
            viewTxtDeskripsiKedua.viewStatus(Miss)
            inputDeskripsi2.getMargins(MBottom,20)
        }
        if (output2.text.isNullOrEmpty()) {
            errOutputKedua = "*Output kedua TA harus diisi"
            viewTxtOutputKedua.text = errOutputKedua
            viewTxtOutputKedua.viewStatus(True)
            inputOutput2.getMargins(MBottom,0)
            viewTxtOutputKedua.getMargins(MTop,5)
            viewTxtOutputKedua.getMargins(MBottom,15)
            validator = false
        } else {
            viewTxtOutputKedua.viewStatus(Miss)
            inputOutput2.getMargins(MBottom,20)
        }
        if (pilihDosenPembimbing1.text.isNullOrEmpty()) {
            errDosenPembimbing1 = "*Harap Pilih dosen Pembimbing 1"
            viewTxtPilihDospem1.text = errDosenPembimbing1
            viewTxtPilihDospem1.viewStatus(True)
            inputDospem1.getMargins(MBottom,0)
            viewTxtPilihDospem1.getMargins(MTop,5)
            viewTxtPilihDospem1.getMargins(MBottom,15)
            validator = false
        } else {
            viewTxtPilihDospem1.viewStatus(Miss)
            inputDospem1.getMargins(MBottom,20)
        }
        if (pilihDosenPembimbing2.text.isNullOrEmpty()) {
            errDosenPembimbing2 = "*Harap Pilih dosen Pembimbing 2"
            viewTxtPilihDospem2.text = errDosenPembimbing2
            viewTxtPilihDospem2.viewStatus(True)
            inputDospem2.getMargins(MBottom,0)
            viewTxtPilihDospem2.getMargins(MTop,5)
            viewTxtPilihDospem2.getMargins(MBottom,20)
            validator = false
        } else {
            viewTxtPilihDospem2.viewStatus(Miss)
            inputDospem2.getMargins(MBottom,20)
        }
        if (validator) {
            Get.dialog(
                this, "Apakah anda yakin", "Ingin mengirim Judul TA ?",
                onClickPositive = {
                    val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    val token: String? = sharedPreference.getString("token", null)
                    if (token !== null) {
                        Network.instance.addSkripsi(
                            textInputJudulTA1.text.toString(),
                            textInputJudulTA2.text.toString(),
                            deskripsi1.text.toString(),
                            deskripsi2.text.toString(),
                            output1.text.toString(),
                            output2.text.toString(),
                            selectedDosenList[0]["id"].toString(),
                            selectedDosenList[1]["id"].toString(),
                            token
                        )
                            .enqueue(object : Callback<Map<String, Any>> {
                                @SuppressLint("WrongViewCast")
                                override fun onResponse(
                                    call: Call<Map<String, Any>>,
                                    response: Response<Map<String, Any>>
                                ) {
                                    if (response.isSuccessful) {
                                        Get.dialogSingle(
                                            this@InputJudulTA,
                                            R.layout.success_dialog,
                                            R.id.successDialogTxt,
                                            R.id.successDialogButton,
                                            "Berhasil input judul TA !",
                                            singleAction = {
                                                Get.back(this@InputJudulTA)
                                            }
                                        )
                                        Toast.makeText(
                                            this@InputJudulTA,
                                            "Berhasil input judul TA!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    } else {
                                        Get.dialogSingle(
                                            this@InputJudulTA,
                                            R.layout.failed_dialog,
                                            R.id.failedDialogTxt,
                                            R.id.failedDialogButton,
                                            "Gagal input judul TA",
                                            singleAction = {
                                                Get.back(this@InputJudulTA)
                                            }
                                        )
                                        Toast.makeText(
                                            this@InputJudulTA,
                                            "Gagal input judul TA",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }

                                override fun onFailure(
                                    call: Call<Map<String, Any>>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(
                                        this@InputJudulTA,
                                        t.message.toString(),
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    Log.e("Terjadi Kesalahan", t.message.toString())
                                }
                            })
                    } else {
                        Get.offAll(this@InputJudulTA, Login::class.java)
                    }


                },
            )
        }

    }

    @SuppressLint("SuspiciousIndentation")
    fun getData() {
        val sharedPreference = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token: String? = sharedPreference.getString("token", null)
        if (token !== null) {
            Network.instance.getChats(token, 100).enqueue(object : Callback<Map<String, Any>> {
                @SuppressLint("WrongViewCast")
                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {
                    val dataResponse = response.body()
                    if (response.isSuccessful && dataResponse != null) {
                        val data = dataResponse["data"] as? List<Map<String, Any>>
                        if (data != null) {
                            itemList.addAll(data)
                        }
                    } else {
                        Toast.makeText(this@InputJudulTA, "Error fetch data", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@InputJudulTA, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            Get.offAll(this@InputJudulTA, Login::class.java)
        }
    }

    private fun scrollAbleEdittext(editText: EditText) {
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

    private fun setupDropDownPilihDosenPembimbing(autoCompleteTextView: AutoCompleteTextView) {

        autoCompleteTextView.setDropDownBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources, R.drawable.filter_spinner_dropdown_bg, null
            )
        )
        val adapter = SimpleAdapter(
            this,
            itemList,
            R.layout.input_judul_ta_list_item,
            arrayOf("name"),
            intArrayOf(R.id.list_dosen_pembb)
        )

        adapter.setViewBinder { view, _, text ->
            if (view is TextView) {
                view.text = text.toString()
                true
            } else {
                false
            }
        }

        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedItem = adapter.getItem(position) as Map<String, Any>?
                autoCompleteTextView.setText(selectedItem?.get("name").toString(), false)

                val selectedDosenMap = HashMap<String, Any>()
                selectedDosenMap["id"] = selectedItem?.get("id") ?: ""
                selectedDosenList.add(selectedDosenMap)
            }
    }

    private fun setupDropDownPilihDosenPembimbing() {
        val pilihDosenPembimbing1: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing1)
        val pilihDosenPembimbing2: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing2)
        setupDropDownPilihDosenPembimbing(pilihDosenPembimbing1)
        setupDropDownPilihDosenPembimbing(pilihDosenPembimbing2)
    }


}