package com.example.taskjet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val db: DatabaseHandler, private val activity: MainActivity) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var todoList: List<Model> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()

        val item = todoList[position]
        holder.task.text = item.task
        holder.task.isChecked = toBoolean(item.status)
        holder.task.setOnCheckedChangeListener { _, isChecked ->
            val newStatus = if (isChecked) 1 else 0
            db.updateStatus(item.id, newStatus)
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun getContext(): MainActivity {
        return activity
    }

    fun setTasks(todoList: List<Model>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val item = todoList[position]
        db.deleteTask(item.id)
        val newList = todoList.toMutableList()
        newList.removeAt(position)
        todoList = newList
        notifyItemRemoved(position)
    }

    fun editItem(position: Int) {
        val item = todoList[position]
        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.task)
        }
        val fragment = NewTask()
        fragment.arguments = bundle
        fragment.show(activity.supportFragmentManager, NewTask.TAG)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: CheckBox = itemView.findViewById(R.id.todoCheckBox)
    }
}
