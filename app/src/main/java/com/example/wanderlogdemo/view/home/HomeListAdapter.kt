package com.example.wanderlogdemo.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bayonefelipecuervoasset.database.domain.ToDoDomain
import com.example.wanderlogdemo.R

class HomeListAdapter(
    private val getTaskList: MutableList<ToDoDomain>
) : RecyclerView.Adapter<HomeListAdapter.TaskRowViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRowViewHolder {
        val transactionView = LayoutInflater.from(parent.context)
            .inflate(R.layout.to_do_task_item, parent, false)
        return TaskRowViewHolder(transactionView)
    }

    override fun getItemCount(): Int {
        return getTaskList.size
    }

    override fun onBindViewHolder(holder: TaskRowViewHolder, position: Int) {
        val currentItem = getTaskList[position]
        holder.taskRowText.text = currentItem.task
        holder.dateToComplete.text = currentItem.date
    }


    fun deleteItem(position: Int) {
        getTaskList.removeAt(position)
        notifyItemRemoved(position)
    }

    class TaskRowViewHolder(itemViewTask: View) :
        RecyclerView.ViewHolder(itemViewTask) {
        val taskRowText: TextView = itemView.findViewById(R.id.taskNameItem)
        val dateToComplete: TextView = itemView.findViewById(R.id.taskDateItem)

    }

}