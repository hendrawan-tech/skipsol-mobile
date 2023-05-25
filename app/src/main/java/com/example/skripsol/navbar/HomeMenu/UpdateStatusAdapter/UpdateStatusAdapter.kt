package com.example.skripsol.navbar.HomeMenu.UpdateStatusAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R

import de.hdodenhof.circleimageview.CircleImageView


class UpdateStatusAdapter(private val itemList: List<UpdateStatusData>) : RecyclerView.Adapter<UpdateStatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_update_status_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.UpdateStatusImage.setImageResource(item.UpdateStatusImage)
        holder.UpdateStatusTitle.text= item.UpdadateStatusTitle
        holder.UpdateStatusDate.text = item.UpdateStatusDate
        if (item.UpdateStatusVerified){
            holder.UpdateStatusVerified.visibility = View.VISIBLE
        }else{
            holder.UpdateStatusVerified.visibility = View.INVISIBLE
        }



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val UpdateStatusImage: CircleImageView = itemView.findViewById(R.id.update_status_image)
        val UpdateStatusTitle: TextView = itemView.findViewById(R.id.update_status_title)
        val UpdateStatusDate: TextView = itemView.findViewById(R.id.update_status_date)
        val UpdateStatusVerified : ImageView = itemView.findViewById(R.id.update_status_verified)
    }
}