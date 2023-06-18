package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.HeadFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import org.w3c.dom.Text
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

//        Input Text
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
            viewTxtJudulPertama.visibility = VISIBLE
            validator = false
        } else {
            viewTxtJudulPertama.visibility = INVISIBLE
        }
        if (deskripsi1.text.isNullOrEmpty()) {
            errDeskripsiPertama = "*Deskripsi TA harus diisi"
            viewTxtDeskripsiPertama.text = errDeskripsiPertama
            viewTxtDeskripsiPertama.visibility = VISIBLE
            validator = false
        } else {
            viewTxtDeskripsiPertama.visibility = INVISIBLE
        }
        if (output1.text.isNullOrEmpty()) {
            errOutputPertama = "*Output TA harus diisi"
            viewTxtOutputPertama.text = errOutputPertama
            viewTxtOutputPertama.visibility = VISIBLE
            validator = false
        } else {
            viewTxtOutputPertama.visibility = INVISIBLE
        }
        if (textInputJudulTA2.text.isNullOrEmpty()) {
            errJudulKeduaTA = "*Judul Kedua TA harus diisi"
            viewTxtJudulKedua.text = errJudulKeduaTA
            viewTxtJudulKedua.visibility = VISIBLE
            validator = false
        } else {
            viewTxtJudulKedua.visibility = INVISIBLE
        }
        if (deskripsi2.text.isNullOrEmpty()) {
            errDeskripsiKedua = "*Deskripsi kedua TA harus diisi"
            viewTxtDeskripsiKedua.text = errDeskripsiKedua
            viewTxtDeskripsiKedua.visibility = VISIBLE
            validator = false
        } else {
            viewTxtDeskripsiKedua.visibility = INVISIBLE
        }
        if (output2.text.isNullOrEmpty()) {
            errOutputKedua = "Output kedua TA harus diisi"
            viewTxtOutputKedua.text = errOutputKedua
            viewTxtOutputKedua.visibility = VISIBLE
            validator = false
        } else {
            viewTxtOutputKedua.visibility = INVISIBLE
        }
        if (pilihDosenPembimbing1.text.isNullOrEmpty()) {
            errDosenPembimbing1 = "*Harap Pilih dosen Pembimbing 1"
            viewTxtPilihDospem1.text = errDosenPembimbing1
            viewTxtPilihDospem1.visibility = VISIBLE
            validator = false
        } else {
            viewTxtPilihDospem1.visibility = INVISIBLE
        }
        if (pilihDosenPembimbing2.text.isNullOrEmpty()) {
            errDosenPembimbing2 = "*Harap Pilih dosen Pembimbing 2"
            viewTxtPilihDospem2.text = errDosenPembimbing2
            viewTxtPilihDospem2.visibility = VISIBLE
            validator = false
        } else {
            viewTxtPilihDospem2.visibility = INVISIBLE
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
                                        )
                                        Toast.makeText(
                                            this@InputJudulTA,
                                            "Berhasil input judul TA!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Get.back(this@InputJudulTA)
                                    } else {
                                        Get.dialogSingle(
                                            this@InputJudulTA,
                                            R.layout.failed_dialog,
                                            R.id.failedDialogTxt,
                                            R.id.failedDialogButton,
                                            "Gagal input judul TA",
                                        )
                                        Toast.makeText(
                                            this@InputJudulTA,
                                            "Gagal input judul TA",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Get.back(this@InputJudulTA)
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
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
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