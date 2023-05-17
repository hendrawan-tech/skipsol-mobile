package com.example.skripsol.navbar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.skripsol.R
import com.example.skripsol.navbar.HomeMenu.FormPengajuan
import com.example.skripsol.navbar.HomeMenu.InputJudulTA
import com.example.skripsol.navbar.HomeMenu.Monitoring
import com.example.skripsol.navbar.HomeMenu.UpdateStatus


class Home : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.home_screen, container, false)

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

        return view

    }

    


}