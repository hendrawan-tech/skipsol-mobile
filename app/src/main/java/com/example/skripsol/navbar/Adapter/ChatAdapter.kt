package com.example.skripsol.navbar.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import de.hdodenhof.circleimageview.CircleImageView


class CardAdapter(private val itemList: List<CardItem>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_chat_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.imageView.setImageResource(item.imageRes)
        holder.titleTextView.text = item.title
        holder.subtitleTextView.text = item.subtitle
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: CircleImageView = itemView.findViewById(R.id.lecture_profile)
        val titleTextView: TextView = itemView.findViewById(R.id.lecture_name)
        val subtitleTextView: TextView = itemView.findViewById(R.id.lecture_number)
    }
}
