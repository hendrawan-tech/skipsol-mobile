package com.example.skripsol.navbar.HomeMenu.BroadcastAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter.UpdateStatusData

import de.hdodenhof.circleimageview.CircleImageView


class BroadcastAdapter(private val itemList: List<BroadcastData>) : RecyclerView.Adapter<BroadcastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_broadcast_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.broadcastImage.setImageResource(item.broadcastImage)
        holder.broadcastTitle.text = item.broadcastTitle
        holder.broadcastSubtitle.text = item.broadcastSubtitle



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val broadcastImage: CircleImageView = itemView.findViewById(R.id.broadcast_image)
        val broadcastTitle: TextView = itemView.findViewById(R.id.broadcast_title)
        val broadcastSubtitle: TextView = itemView.findViewById(R.id.broadcast_subtitle)

    }
}