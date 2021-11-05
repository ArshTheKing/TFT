package com.example.bluetoothlock.extras

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.bluetoothlock.R
import com.example.bluetoothlock.objects.Message

class MessageAdapter(val context: Context, val msgList:ArrayList<Message>): Adapter<RecyclerView.ViewHolder>(){

    //ItemView types
    val ITEM_SEND=1
    val ITEM_RECEIVED=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==1){
            val view=LayoutInflater.from(context).inflate(R.layout.sendmessage,parent,false)
            return SentViewHolder(view)
        }else{
            val view=LayoutInflater.from(context).inflate(R.layout.entrymessage,parent,false)
            return ReceiveViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var message=msgList[position]

        if(holder.javaClass== SentViewHolder::class.java){
            var viewHolder = holder as SentViewHolder
            holder.sentMessage.text= message.content
        } else{
            var viewHolder= holder as ReceiveViewHolder
            holder.recievedMessage.text= message.content
        }
    }

    override fun getItemViewType(position: Int): Int {
        val msg=msgList[position]
        if (msg.sent==true) return ITEM_SEND
        else return ITEM_RECEIVED
    }

    override fun getItemCount(): Int {
        return msgList.size
    }
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var sentMessage=itemView.findViewById<TextView>(R.id.sentcontent)
    }
    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var recievedMessage=itemView.findViewById<TextView>(R.id.recivedcontent)
    }
}
