package com.example.route.ui.tabs.TasksList

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.route.R
import com.example.route.databinding.ItemListBinding
import com.example.route.model.Task
import kotlin.coroutines.coroutineContext


class TasksAdapter(var tasksList:MutableList<Task>?):RecyclerView.Adapter<TasksAdapter.viewHolder>(){

  class viewHolder(val itemBinding: ItemListBinding):RecyclerView.ViewHolder(itemBinding.root){

      fun bind(task: Task){
          itemBinding.title.text = task.title
          itemBinding.description.text=task.description
      }

  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
         val itemBinding = ItemListBinding.inflate(
             LayoutInflater.from(parent.context),
             parent,
             false
         )

        return viewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return tasksList!!.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(tasksList!![position])

        if(onItemDeleteListener!=null){
            holder.itemBinding.deleteView.setOnClickListener {
                holder.itemBinding.swipeLayout.close(true)
                onItemDeleteListener?.onItemClick(position, tasksList!![position])
            }
        }

        if(tasksList!![position].isDone==true){
            holder.itemBinding.doneBtn.setBackgroundColor(Color.WHITE)
            holder.itemBinding.doneBtn.setImageResource(R.drawable.ic_done_green)
            holder.itemBinding.title.setTextColor(Color.GREEN)
        }

        if(onCardClickedListener!=null){
            holder.itemBinding.card.setOnLongClickListener(View.OnLongClickListener {
                onCardClickedListener?.onItemClicked(tasksList!![position])
                true
            })
        }



    }

    fun bindTasks(tasks: MutableList<Task>) {
        this.tasksList=tasks
        notifyDataSetChanged()
    }
    fun taskDeleted(task:Task) {
        val position = tasksList?.indexOf(task)
        tasksList?.remove(task)
        notifyItemRemoved(position!!)
    }

    fun taskUpdated(task:Task) {
        val index:Int = tasksList!!.indexOf(task)
        tasksList!![index].isDone=true
       notifyItemChanged(index)
    }



    var onCardClickedListener:OnItemClickedListener?=null
    var onItemDeleteListener:OnItemClickListener?=null

    fun interface OnItemClickListener{
        fun onItemClick(position: Int,task: Task)
    }

    fun interface OnItemClickedListener{
        fun onItemClicked(task: Task)
    }






}