package com.connaughttechnologies.lovedonce.ui.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.MemorialProfile

class ProfileAdapter(val list: List<MemorialProfile>, val listener: ClickListener) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    interface ClickListener {
        fun onClick(profile: MemorialProfile)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutContainer: ViewGroup = itemView.findViewById<ViewGroup>(R.id.layoutContainer)
        private val ivProfile: ImageView = itemView.findViewById<ImageView>(R.id.ivProfile)
        private val tvProfileName: TextView = itemView.findViewById<TextView>(R.id.tvProfileName)
        fun bind(data: MemorialProfile) {
            Glide.with(ivProfile).load(data.profileImage).into(ivProfile)
            tvProfileName.text = data.firstName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_profile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.layoutContainer.setOnClickListener {
            listener.onClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}