package com.example.skripsol.navbar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.skripsol.R
import com.example.skripsol.navbar.HomeMenu.*
import com.example.skripsol.state.MyState
import com.google.android.material.textview.MaterialTextView


class Home : Fragment() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_screen, container, false)

        val user = MyState.getDataUser()

        val username: MaterialTextView = view.findViewById(R.id.user_username)
        val nim: MaterialTextView = view.findViewById(R.id.nim)
        val name: MaterialTextView = view.findViewById(R.id.name)
        val fakultas: MaterialTextView = view.findViewById(R.id.fakultas)
        val kampus: MaterialTextView = view.findViewById(R.id.kampus)

        val tmpFakultas =
            user?.get("program_studi")?.let { it as? Map<*, *> }?.get("name").toString().split(" ")
        var valFakultas: String = ""
        var valKampus: String = ""

        if (tmpFakultas.size > 2) {
            valFakultas = "${tmpFakultas[0]} ${tmpFakultas[1]}"
            valKampus = tmpFakultas[2]
        } else {
            valFakultas = user?.get("program_studi")?.let { it as? Map<*, *> }?.get("name").toString()

        }

        username.text = user?.get("name").toString()
        nim.text = user?.get("nim").toString()
        name.text = user?.get("name").toString()
        fakultas.text = valFakultas
        kampus.text = "Politeknik Negeri Jember - Kampus $valKampus"


        view.findViewById<CardView>(R.id.btn_input_judul_ta).setOnClickListener {
            val intent = Intent(context, InputJudulTA::class.java)
            startActivity(intent)

        }
        view.findViewById<CardView>(R.id.btn_monitoring).setOnClickListener {
            val intent = Intent(context, Monitoring::class.java)
            startActivity(intent)

        }
        view.findViewById<CardView>(R.id.btn_form_pengajuan).setOnClickListener {
            val intent = Intent(context, FormPengajuan::class.java)
            startActivity(intent)

        }
        view.findViewById<CardView>(R.id.btn_update_status).setOnClickListener {
            val intent = Intent(context, UpdateStatus::class.java)
            startActivity(intent)
        }

        view.findViewById<ImageView>(R.id.btn_notifikasi).setOnClickListener {
            val intent = Intent(context, Broadcast::class.java)
            startActivity(intent)
        }

        view.findViewById<TextView>(R.id.btn_lihat_semua_notifikasi).setOnClickListener {
            val intent = Intent(context, Broadcast::class.java)
            startActivity(intent)
        }

        return view

    }


}