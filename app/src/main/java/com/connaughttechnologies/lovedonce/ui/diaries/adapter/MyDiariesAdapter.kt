package com.connaughttechnologies.lovedonce.ui.diaries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.DiaryData

class MyDiariesAdapter(val list: List<DiaryData>, val listener: ClickListener) :
    RecyclerView.Adapter<MyDiariesAdapter.ViewHolder>() {

    interface ClickListener {
        fun onClick(id: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDiary = itemView.findViewById<TextView>(R.id.tvDiary)
        val layoutMyDiary = itemView.findViewById<ViewGroup>(R.id.layoutMyDiary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_my_diaries, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.tvDiary.text = data.dailyAffirmation
        holder.layoutMyDiary.setOnClickListener {
            listener.onClick(data.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}