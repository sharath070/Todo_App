package com.sharath070.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharath070.todoapp.databinding.CardItemBinding
import com.sharath070.todoapp.db.Todo

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var todoList = emptyList<Todo>()

    class ViewHolder(val itemBinding: CardItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.itemBinding.tvTitle.text = currentItem.title
        holder.itemBinding.tvNote.text = currentItem.note
    }

    fun setData(todos: List<Todo>){
        this.todoList = todos
        notifyDataSetChanged()
    }



}