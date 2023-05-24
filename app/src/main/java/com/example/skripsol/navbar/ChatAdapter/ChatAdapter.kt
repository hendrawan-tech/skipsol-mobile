package com.example.skripsol.navbar.ChatAdapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import de.hdodenhof.circleimageview.CircleImageView


class ChatAdapter(private var itemList: List<Map<String, Any>>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    private var originalItemList: List<Map<String, Any>> = itemList.toList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycle_chat_screen, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(res: Map<String, Any>) {
            with(itemView) {
                val imageView: CircleImageView = itemView.findViewById(R.id.lecture_profile)
                val dosenName: TextView = itemView.findViewById(R.id.lecture_name)
                val dosenPhoneNumber: TextView = itemView.findViewById(R.id.lecture_number)

                dosenName.text = res["name"].toString()
                dosenPhoneNumber.text = res["phone_number"].toString() ?: "-"

                itemView.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("https://api.whatsapp.com/send?phone=${dosenPhoneNumber.text}")
                    itemView.context.startActivity(intent)
                }
            }


        }
    }

    fun filter(text: String) {
        itemList = if (text.isNotEmpty()) {
            val searchText = text.toLowerCase()
            originalItemList.filter { item ->
                val name = item["name"].toString().toLowerCase()
                val phoneNumber = item["phone_number"].toString().toLowerCase()
                name.contains(searchText) || phoneNumber.contains(searchText)
            }
        } else {
            originalItemList
        }
        notifyDataSetChanged()
    }
}
