package com.example.skripsol.navbar


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import com.example.skripsol.navbar.RiwayatJudulAdapter.RiwayatJudulAdapter
import com.example.skripsol.navbar.RiwayatJudulAdapter.RiwayatJudulData

class RiwayatJudul : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatJudulAdapter
    private val itemList = mutableListOf<RiwayatJudulData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.history_screen, container, false)
        recyclerView = view.findViewById(R.id.history_recycle_view)

        // Tambahkan item ke daftar
        for (i in 1..10) {
            itemList.add(
                RiwayatJudulData(
                    R.drawable.img_riwayat_judul,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
                    "$i Mei 200$i"
                )
            )
        }

        // Atur layout manager dan adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RiwayatJudulAdapter(itemList)
        recyclerView.adapter = adapter

        return view
    }


}