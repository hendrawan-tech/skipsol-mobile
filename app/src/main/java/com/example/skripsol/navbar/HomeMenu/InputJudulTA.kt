package com.example.skripsol.navbar.HomeMenu


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InputJudulTA : AppCompatActivity() {

    private val itemList = ArrayList<Map<String, Any>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_judul_ta_screen)
        getData()
        val textInputJudulTA: TextInputLayout = findViewById(R.id.InputLayout_input_judul_ta)
        val editTextJudulTA: EditText = findViewById(R.id.EditText_input_judul_ta)

        val inputLayoutAbstrakTA: TextInputLayout = findViewById(R.id.InputLayout_abstrak_ta)
        val editTextAbstrakTA: EditText = findViewById(R.id.EditText_abstrak_ta)
        val btnKirimInputJudulTA: MaterialButton = findViewById(R.id.btn_kirim_input_judul_ta)

        btnKirimInputJudulTA.setOnClickListener {
            Get.dialog(
                this, "Apakah anda yakin", "Ingin mengirim Judul TA ?",
                onClickPositive = {

                },
                onCLickNegative = {

                },

                )

        }
        setupDropDownPilihDosenPembimbing()
        scrollAbleEdittext(editTextAbstrakTA)
        findViewById<ImageView>(R.id.btn_back_input_judul_ta).setOnClickListener {
            Get.back(this)
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
                        Toast.makeText(this@InputJudulTA, "Error fetch data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(this@InputJudulTA, t.message.toString(), Toast.LENGTH_SHORT).show()
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
        val adapter = SimpleAdapter(this, itemList, R.layout.input_judul_ta_list_item, arrayOf("name"), intArrayOf(R.id.list_dosen_pembb))

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
                val selectedItem = adapter.getItem(position) as Map<String, Any>
                val selectedName = selectedItem["name"].toString()
                autoCompleteTextView.setText(selectedName, false)
            }
    }

    private fun setupDropDownPilihDosenPembimbing() {
        val pilihDosenPembimbing1: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing1)
        val pilihDosenPembimbing2: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing2)
        val pilihDosenPembimbing3: AutoCompleteTextView = findViewById(R.id.pilih_dosen_pembimbing3)

        setupDropDownPilihDosenPembimbing(pilihDosenPembimbing1)
        setupDropDownPilihDosenPembimbing(pilihDosenPembimbing2)
        setupDropDownPilihDosenPembimbing(pilihDosenPembimbing3)
        Log.e("asd", pilihDosenPembimbing1.text.toString())
    }


}