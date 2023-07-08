package com.example.sdk_chat_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList:ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view);
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SentViewHolder(view);
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val curMsg = messageList[position]

        if(FirebaseAuth.getInstance().currentUser?.uid.equals(curMsg.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curMsg = messageList[position]
        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder

            holder.sentMsg.text = curMsg.massage
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMsg.text = curMsg.massage
        }
    }

    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val sentMsg = itemView.findViewById<TextView>(R.id.sendTxt)

    }

    class ReceiveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val receiveMsg = itemView.findViewById<TextView>(R.id.receiveTxt)
    }
}