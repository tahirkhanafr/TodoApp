package com.example.todolist.dialogue

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.res.Resources.getSystem
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTodoPopupBinding
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding    : FragmentAddTodoPopupBinding
    private lateinit var listener   : DialogNextBtnClickListener
    private var toDoData            : ToDoData? = null
    private  var longTime   : Long = 0

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTodoPopupFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        //initialize
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString(),
                arguments?.getString("todoDate").toString(),
                arguments?.getString("todoTime").toString(),
            )
            val input = toDoData?.task
            val pattern = """\{(.*?)\}""".toRegex()
            val match = input?.let { pattern.find(it)?.groupValues?.get(1) }
            println(match)

            val value=match
            val todo = value?.split(", ")
            val todoTime = todo?.get(0)?.split("=")?.get(1)
            val todoDate = todo?.get(1)?.split("=")?.get(1)
            val todoTask = todo?.get(2)?.split("=")?.get(1)

            binding.edTask.setText(todoTask)
            binding.edDate.setText(todoDate)
            binding.edTime.setText(todoTime)
//            binding.edTask.setText(toDoData?.task)
        }

        // pick up date
        binding.imgDate.setOnClickListener() {
            // create new instance of DatePickerFragment
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY", viewLifecycleOwner
            )
            { resultKey, bundle ->
                if (resultKey == "REQUEST_KEY") {
                    val date = bundle.getString("SELECTED_DATE")
                    binding.edDate.setText(date)
                }
            }
            // show
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        // pick up time
        binding.imgTime.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)
            TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val time = "$hourOfDay:$minute"

                    binding.edTime.setText(time)


                },
                startHour,
                startMinute,
                false
            ).show()
        }

        //used 2
        registerEvent()

    }


    private fun registerEvent() {
        binding.btnAddTask.setOnClickListener {
            val todoTask = binding.edTask.text.toString()
            val todoDate = binding.edDate.text.toString()
            val todoTime = binding.edTime.text.toString()

            if (todoTask.isNotEmpty() && todoDate.isNotEmpty() && todoTime.isNotEmpty()) {
                if (toDoData == null) {
                    listener.onSaveTask(todoTask,todoDate,todoTime, binding.edTask)
                } else {
                    toDoData?.task = todoTask
                    listener.onUpdateTask(toDoData!!,todoTask, todoDate,todoTime, binding.edTask)
                }

            } else {
                Toast.makeText(context, "Please fill all field", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoDate: String, todoTime:String,todoET: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData,todoTask:String ,todoDate: String, todoTime:String, todoET: TextInputEditText)
    }

}