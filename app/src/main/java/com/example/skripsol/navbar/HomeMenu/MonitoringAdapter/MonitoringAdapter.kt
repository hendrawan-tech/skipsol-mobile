package com.example.skripsol.navbar.HomeMenu.MonitoringAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsol.R
import de.hdodenhof.circleimageview.CircleImageView

class MonitoringAdapter(private val itemList: ArrayList<Map<String, Any>>) : RecyclerView.Adapter<MonitoringAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_monitoring_screen, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.montoringImage.setImageResource(R.drawable.img_model_profile)
        holder.monitoringTitle.text= item["deskripsi"].toString()
        holder.monitoringDate.text = item["created_at"].toString()


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val montoringImage: CircleImageView = itemView.findViewById(R.id.monitoring_image)
        val monitoringTitle: TextView = itemView.findViewById(R.id.monitoring_title)
        val monitoringDate: TextView = itemView.findViewById(R.id.monitoring_date)
    }
}