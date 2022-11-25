package com.connaughttechnologies.lovedonce.ui.profiledetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.MemorialProfile

class ProfileDetailAdapter(private val images: List<String>, val listener: ClickListener) :
    RecyclerView.Adapter<ProfileDetailAdapter.ViewHolder>() {
    interface ClickListener {
        fun onClick()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProfile = itemView.findViewById<ImageView>(R.id.ivProfile)
        fun bind(image: String) {
            Glide.with(ivProfile).load(image).into(ivProfile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_profile_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}