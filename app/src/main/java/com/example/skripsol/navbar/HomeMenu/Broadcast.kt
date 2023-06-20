package com.example.skripsol.navbar.HomeMenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.navbar.HomeMenu.BroadcastAdapter.BroadcastAdapter
import com.example.skripsol.navbar.HomeMenu.BroadcastAdapter.BroadcastData

class Broadcast : AppCompatActivity() {

    private lateinit var broadcastRecyclerView: RecyclerView

    private lateinit var broadcastAdapter: BroadcastAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.broadcast_screen)

        broadcastRecyclerView = findViewById(R.id.recycle_broadcast)
        broadcastAdapter = BroadcastAdapter(generateRandomNotif())
        broadcastRecyclerView.layoutManager = LinearLayoutManager(this)
        broadcastRecyclerView.adapter = broadcastAdapter

        findViewById<ImageView>(R.id.btn_back_broadcast).setOnClickListener {
            Get.back(this)
        }
    }

    private fun generateRandomNotif(): List<BroadcastData> {

        val randomNotif = mutableListOf<BroadcastData>()

        repeat(10) {
            val broadcastTitle = arrayOf(
                "Review Proposal",
                "Analisis Data",
                "Pembahasan Hasil",
                "Penyusunan Bab Penutup",
                "Finalisasi Skripsi"
                // Tambahkan judul pertemuan lainnya sesuai dengan kebutuhan
            ).random()


            val broadcastSubtitle = arrayOf(
                "Pertemuan 1: Diskusi mengenai topik skripsi",
                "Pertemuan 2: Riset literatur terkait skripsi",
                "Pertemuan 3: Pembahasan metodologi penelitian",
                "Pertemuan 4: Analisis data skripsi",
                "Pertemuan 5: Penulisan bab-bab skripsi",
                // Tambahkan elemen subtitle lainnya sesuai dengan pertemuan bimbingan skripsi yang ada
            ).random()
        randomNotif.add(
            BroadcastData(
            R.drawable.img_broadcast,
            broadcastTitle,
            broadcastSubtitle
        )
        )

        }
        return randomNotif


    }
}