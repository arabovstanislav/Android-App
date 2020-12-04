 package com.e.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.mynotes.R
import com.e.mynotes.adapter.TaskAdapter
import com.e.mynotes.database.AppDatabase
import com.e.mynotes.databinding.FragmentMainBinding
import com.e.mynotes.model.Task
import kotlinx.coroutines.launch

 class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        database = AppDatabase.getInstance(binding.root.context)

        binding.recyclerView.adapter = TaskAdapter(binding.root.context,getData(),navController)
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context)

        binding.floatingActionButtonAdd.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_createTaskFragment)
        }
    }

    private fun getData(): List<Task> {
        val list: MutableList<Task> = mutableListOf()
        launch {
            val cursor = database.readAllData()
            //database.deleteDatabase()
            while(cursor.moveToNext()){
                val task = Task(cursor.getLong(0) ,cursor.getString(1) , cursor.getString(2))
                //Log.d("LIST" , "${task.id} ,${task.name} , ${task.description}")
                list.add(task)
            }
        }
        return list
    }

}