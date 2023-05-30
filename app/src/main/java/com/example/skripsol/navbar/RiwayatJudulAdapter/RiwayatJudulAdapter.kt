package com.example.skripsol.navbar.RiwayatJudulAdapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import com.example.skripsol.navbar.ChatAdapter.ChatAdapter
import de.hdodenhof.circleimageview.CircleImageView

class RiwayatJudulAdapter(private var itemList: List<Map<String, Any>>) :
    RecyclerView.Adapter<RiwayatJudulAdapter.ViewHolder>() {

    private var originalItemList: List<Map<String, Any>> = itemList.toList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiwayatJudulAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycle_history_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatJudulAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(res: Map<String, Any>) {
            with(itemView) {
                val imageView: CircleImageView = itemView.findViewById(R.id.history_title_image)
                val titleTextView: TextView = itemView.findViewById(R.id.history_title)
                val subtitleTextView: TextView = itemView.findViewById(R.id.history_date)

                titleTextView.text = res["judul_1"].toString()
                subtitleTextView.text = res["created_at"].toString() ?: "-"
            }
        }
    }
}