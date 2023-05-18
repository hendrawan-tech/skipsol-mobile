package com.example.skripsol.navbar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.ChatAdapter.ChatAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Chat : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatAdapter
    private val itemList = ArrayList<Map<String, Any>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_screen, container, false)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView = view.findViewById(R.id.chat_recycle_view)
        recyclerView.layoutManager = layoutManager

        fun getData() {
            val sharedPreference =
                requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val token: String? = sharedPreference.getString("token", null)
            if (token !== null) {
                Network.instance.getChats(token, 10).enqueue(object : Callback<Map<String, Any>> {
                    @SuppressLint("WrongViewCast")
                    override fun onResponse(
                        call: Call<Map<String, Any>>,
                        response: Response<Map<String, Any>>
                    ) {
                        val dataResponse = response.body()
                        if (response.isSuccessful && dataResponse != null) {
                            val data = dataResponse["data"] as? Map<String, Any>
                            val user = data?.get("user") as? Map<String, Any>
                            val dataList = user?.get("data") as? List<Map<String, Any>>
                            if (dataList != null) {
                                itemList.addAll(dataList)
                                adapter = ChatAdapter(itemList)
                                recyclerView.adapter = adapter
                            }
                        } else {
                            Toast.makeText(requireContext(), "Error fetch data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                        Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            } else {
                val intent = Intent(requireContext(), Login::class.java)
                startActivity(intent)
            }
        }
        getData()

        return view
    }
}