package com.example.skripsol.navbar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import com.example.skripsol.navbar.ChatAdapter.CardAdapter
import com.example.skripsol.navbar.ChatAdapter.CardItem

class Chat : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter
    private val itemList = mutableListOf<CardItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_screen, container, false)
        recyclerView = view.findViewById(R.id.recycle_view)

        // Tambahkan item ke daftar
        for (i in 1..500) {
            itemList.add(CardItem(R.drawable.img_model_profile_home, "Judul $i", "Deskripsi $i"))
        }

        // Atur layout manager dan adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CardAdapter(itemList)
        recyclerView.adapter = adapter

        return view
    }


}