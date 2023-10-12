package com.example.todolisttutorial

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolisttutorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private  val taskViewModel: TaskViewModel by viewModels {
        TaskItemModelFactory((application as TodoApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val mainActivity = this
        taskViewModel.taskItems.observe(this) {
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem)
    {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem)
    {
        taskViewModel.setCompleted(taskItem)
    }
}