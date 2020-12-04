package com.e.mynotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.e.mynotes.database.AppDatabase
import com.e.mynotes.databinding.FragmentUpdateTaskBinding
import com.e.mynotes.model.Task
import kotlinx.coroutines.launch


class UpdateTaskFragment : BaseFragment() {

    private lateinit var binding: FragmentUpdateTaskBinding
    private val args: UpdateTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        database = AppDatabase.getInstance(binding.root.context)

        binding.taskName.setText(args.taskName)
        binding.taskDescription.setText(args.description)

        binding.buttonUpdate.setOnClickListener {
            launch {
                val task = Task(args.id,binding.taskName.text.toString(),binding.taskDescription.text.toString())
                database.updateTask(task)
            }
            activity?.onBackPressed()
        }

        binding.buttonDelete.setOnClickListener {
            launch {
                val task = Task(args.id,binding.taskName.text.toString(),binding.taskDescription.text.toString())
                database.deleteTask(task)
            }
            activity?.onBackPressed()
        }

    }

}