package com.connaughttechnologies.lovedonce.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.responses.ResponseGetServiceToken
import com.connaughttechnologies.lovedonce.extensions.toFormatTime
//import com.twilio.chat.Message

class ChatAdapter(val response: ResponseGetServiceToken, val list:List<Any> /*List<Message>*/) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutSender = itemView.findViewById<ViewGroup>(R.id.layoutSender)
        val tvMessageSender = itemView.findViewById<TextView>(R.id.tvMessageSender)
        val tvSenderTime = itemView.findViewById<TextView>(R.id.tvSenderTime)
        val layoutReceiver = itemView.findViewById<ViewGroup>(R.id.layoutReceiver)
        val tvMessageReceiver = itemView.findViewById<TextView>(R.id.tvMessageReceiver)
        val tvReceiverTime = itemView.findViewById<TextView>(R.id.tvReceiverTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_chat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = list[position]
        /*if (message.author == response.identity) {
            holder.layoutSender.visibility = View.VISIBLE
            holder.layoutReceiver.visibility = View.GONE
            holder.tvMessageSender.text = message.messageBody
            holder.tvSenderTime.text = message.dateCreatedAsDate.toFormatTime()
        } else {
            holder.layoutSender.visibility = View.GONE
            holder.layoutReceiver.visibility = View.VISIBLE
            holder.tvMessageReceiver.text = message.messageBody
            holder.tvReceiverTime.text = message.dateCreatedAsDate.toFormatTime()
        }*/
    }

    override fun getItemCount(): Int {
        return list.size
    }
}