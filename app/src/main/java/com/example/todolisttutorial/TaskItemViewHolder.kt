package com.example.todolisttutorial

import android.content.Context
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todolisttutorial.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root)
{
    @RequiresApi(Build.VERSION_CODES.O)
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name

        if(taskItem.isCompleted()) {
            binding.name.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.dueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        binding.completedButton.setImageResource(taskItem.imageResource())
        binding.completedButton.setColorFilter(taskItem.imageColor(context))

        binding.completedButton.setOnClickListener { clickListener.completeTaskItem(taskItem) }
        binding.taskCellContainer.setOnClickListener { clickListener.editTaskItem(taskItem) }

        if(taskItem.dueTime() != null)
            binding.dueTime.text = timeFormat.format(taskItem.dueTime())
        else
            binding.dueTime.text = ""
    }
}