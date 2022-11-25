package com.connaughttechnologies.lovedonce.ui.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.Service
import com.connaughttechnologies.lovedonce.extensions.formatPrice

class CartAdapter(val list: List<Service>, val listener: ClickListener) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    interface ClickListener {
        fun onCartRemove(item: Service)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivService = itemView.findViewById<ImageView>(R.id.ivService)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvPrice = itemView.findViewById<TextView>(R.id.tvPrice)
        val ivBtnRemove = itemView.findViewById<ImageView>(R.id.ivBtnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.tvTitle.text = data.name
        holder.tvPrice.text = data.price.formatPrice()
        holder.ivBtnRemove.setOnClickListener {
            listener.onCartRemove(data)
        }
        data.coverImg?.let {
            Glide.with(holder.ivService).load(data.coverImg).into(holder.ivService)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}