package com.example.skripsol.navbar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
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
    private lateinit var animationView: LottieAnimationView
    private lateinit var notFoundChat : LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_screen, container, false)
        val layoutManager = LinearLayoutManager(requireContext())
        val EditTextSearchDosen: EditText = view.findViewById(R.id.search_dosen)
        recyclerView = view.findViewById(R.id.chat_recycle_view)
        recyclerView.layoutManager = layoutManager
        animationView = view.findViewById(R.id.animation_view)
        notFoundChat = view.findViewById(R.id.chat_not_found)
        setupTextWatcher(EditTextSearchDosen)

        getData(EditTextSearchDosen)
        return view
    }


    private fun setupTextWatcher(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()
                adapter.filter(searchText)
                if (adapter.itemCount == 0) {
                    notFoundChat.visibility = View.VISIBLE

                } else {
                    notFoundChat.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getData(editText: EditText) {
        animationView.visibility = View.VISIBLE
        animationView.playAnimation()
        val sharedPreference =
            requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
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
                            adapter = ChatAdapter(itemList)
                            recyclerView.adapter = adapter
                            if (animationView.visibility == View.VISIBLE) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }

                            // Pindahkan pemanggilan setupTextWatcher ke sini setelah adapter diinisialisasi
                            setupTextWatcher(editText)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error fetch data", Toast.LENGTH_SHORT)
                            .show()
                        animationView.cancelAnimation()
                        animationView.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                    animationView.visibility = View.VISIBLE
                    animationView.playAnimation();

                }
            })
        } else {
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
        }
    }

}

