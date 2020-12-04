package com.e.mynotes.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation
import com.e.mynotes.database.AppDatabase
import com.e.mynotes.databinding.FragmentCreateTaskBinding
import com.e.mynotes.model.Task
import kotlinx.coroutines.launch

private const val TAG = "CREATED"

class CreateTaskFragment : BaseFragment(){

    private lateinit var binding: FragmentCreateTaskBinding
    private val creationId = 1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentCreateTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        database = AppDatabase.getInstance(binding.root.context)
        binding.buttonAdd.setOnClickListener{
            if(!(binding.taskName.text.toString().isNullOrEmpty())) {
                launch { createTask() }
                activity?.onBackPressed()
            }
        }
    }

    private fun createTask() {
        val task = Task(
            creationId,
            binding.taskName.text.toString(),
            binding.taskDescription.text.toString(),
        )
        Log.d(TAG, "${task.id} , ${task.name} , ${task.description} ")
        database.addTask(task)
    }

}