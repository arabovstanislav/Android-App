package com.e.mynotes.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.e.mynotes.R
import com.e.mynotes.model.Task
import com.e.mynotes.ui.MainFragment
import com.e.mynotes.ui.MainFragmentDirections

private const val TAG = "PARSED"

class TaskAdapter(

    private val context: Context,
    private val toDoList: List<Task>,
    private val navController: NavController

) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewID: TextView = view.findViewById(R.id.task_id)
        val textViewTitle: TextView = view.findViewById(R.id.text_view_title)
        val textViewDescription: TextView = view.findViewById(R.id.text_view_desciption)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false)
        val taskViewHolder = TaskViewHolder(adapterLayout)

        adapterLayout.setOnClickListener{
            val id = taskViewHolder.textViewID.text.toString().toLong()
            val name = taskViewHolder.textViewTitle.text.toString()
            val description = taskViewHolder.textViewDescription.text.toString()

            val action = MainFragmentDirections.actionMainFragmentToUpdateTaskFragment(name,description,id)
            Log.d(TAG , "$id , $name , $description")
            navController.navigate(action)
        }

        return taskViewHolder
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.textViewID.text = toDoList[position].id.toString()
        holder.textViewTitle.text = toDoList[position].name
        holder.textViewDescription.text = toDoList[position].description
    }

    override fun getItemCount(): Int = toDoList.size
}