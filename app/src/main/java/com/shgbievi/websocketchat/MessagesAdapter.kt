package com.shgbievi.websocketchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.msg_item.view.*
import java.util.*


class MessagesAdapter(private var messages: MutableList<Message>) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {


    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFrom = itemView.tv_from
        val tvMsg = itemView.tv_msg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.msg_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int =
        messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.tvFrom.text = messages[position].user
        holder.tvMsg.text = messages[position].message
    }

    fun updateData(list: MutableList<Message>) {
        val diffResult = DiffUtil.calculateDiff(MessagesDiffUtilCallback(messages, list))
        diffResult.dispatchUpdatesTo(this)
        messages = list
    }

    fun addMessage(msg:Message){
        val list = ArrayList(messages)
        list.add(msg)
        val diffResult = DiffUtil.calculateDiff(MessagesDiffUtilCallback(messages, list))
        diffResult.dispatchUpdatesTo(this)
        messages = list
    }

    class MessagesDiffUtilCallback internal constructor(
        private val oldList: List<Message>,
        private val newList: List<Message>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.id == new.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old == new
        }
    }
}