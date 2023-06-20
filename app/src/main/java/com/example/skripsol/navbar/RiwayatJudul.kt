package com.example.skripsol.navbar


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.auth.Login
import com.example.skripsol.config.Network
import com.example.skripsol.navbar.RiwayatJudulAdapter.RiwayatJudulAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatJudul : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RiwayatJudulAdapter
    private val itemList = ArrayList<Map<String, Any>>()
    private lateinit var notFoundRiwayat : LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.history_screen, container, false)
        recyclerView = view.findViewById(R.id.history_recycle_view)

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        notFoundRiwayat = view.findViewById(R.id.riwayat_not_found)
        getData()

        // Atur layout manager dan adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RiwayatJudulAdapter(itemList)
        recyclerView.adapter = adapter

        return view
    }

    private fun getData() {
        val sharedPreference =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token: String? = sharedPreference.getString("token", null)
        if (token !== null) {
            Network.instance.getHistoryJudul(token).enqueue(object : Callback<Map<String, Any>> {
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
                            adapter = RiwayatJudulAdapter(itemList)
                            recyclerView.adapter = adapter
                            if (data.isEmpty()) {
                                notFoundRiwayat.visibility = View.VISIBLE
                            } else {
                                notFoundRiwayat.visibility = View.GONE
                            }
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
            Get.offAll(context, Login::class.java)
        }
    }
}