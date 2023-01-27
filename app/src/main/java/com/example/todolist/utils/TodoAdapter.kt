package com.example.todolist.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.BuildConfig.DEBUG
import com.example.todolist.databinding.EachItemBinding

class TodoAdapter(private val list: MutableList<ToDoData>) :
    RecyclerView.Adapter<TodoAdapter.ToDoViewHolder>()
{
    private var listener :ToDoAdapterClicksInterface?=null
    fun setListener(listener:ToDoAdapterClicksInterface){
        this.listener=listener
    }
    inner class ToDoViewHolder(val binding: EachItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {

        val binding=EachItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        with(holder){
            with(list[position]){

                val input = task
                val pattern = """\{(.*?)\}""".toRegex()
                val match = pattern.find(input)?.groupValues?.get(1)
                println(match)

                val value=match
                val todo = value?.split(", ")
                val todoTime = todo?.get(0)?.split("=")?.get(1)
                val todoDate = todo?.get(1)?.split("=")?.get(1)
                val todoTask = todo?.get(2)?.split("=")?.get(1)

                binding.title.text=todoTask
                binding.date.text=todoDate
                binding.time.text=todoTime

                binding.edit.setOnClickListener{
                    listener?.onEditTaskBtnCicked(this)
                }
                binding.delete.setOnClickListener {
                    listener?.onDeleteTaskBtnCicked(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ToDoAdapterClicksInterface{
        fun onDeleteTaskBtnCicked(toDoData: ToDoData)
        fun onEditTaskBtnCicked(toDoData: ToDoData)
    }
}