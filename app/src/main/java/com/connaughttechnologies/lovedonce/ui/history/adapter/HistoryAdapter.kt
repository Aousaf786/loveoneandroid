package com.connaughttechnologies.lovedonce.ui.history.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.OrderListing
import com.connaughttechnologies.lovedonce.extensions.formatPrice

class HistoryAdapter(val list: List<OrderListing>, val listener: ClickListener) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    interface ClickListener {
        fun onClick(item: OrderListing)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutOrder = itemView.findViewById<ViewGroup>(R.id.layoutOrder)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDesc = itemView.findViewById<TextView>(R.id.tvDesc)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.layoutOrder.setOnClickListener {
            listener.onClick(data)
        }
        holder.tvTitle.text = data.name
        holder.tvDesc.text = data.description
        holder.tvPrice.text = data.totalPrice.formatPrice()
        holder.tvDate.text = data.orderedDate
    }

    override fun getItemCount(): Int {
        return list.size
    }
}