package com.connaughttechnologies.lovedonce.ui.services.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.Service

class ServicesAdapter(
    private val list: List<Service>,
    val listener: ClickListener
) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    interface ClickListener {
        fun onClick(id: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentServices = itemView.findViewById<ViewGroup>(R.id.contentServices)
        val ivService = itemView.findViewById<ImageView>(R.id.ivService)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.contentServices.setOnClickListener {
            listener.onClick(data.id)
        }
        holder.tvTitle.text = data.name
        data.coverImg?.let {
            Glide.with(holder.ivService).load(data.coverImg).into(holder.ivService)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}