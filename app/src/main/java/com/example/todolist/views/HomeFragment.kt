package com.example.todolist.views

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.dialogue.AddTodoPopupFragment
import com.example.todolist.notification.*
import com.example.todolist.notification.Notification
import com.example.todolist.repository.UserModel
import com.example.todolist.utils.ToDoData
import com.example.todolist.utils.TodoAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class HomeFragment : Fragment(), AddTodoPopupFragment.DialogNextBtnClickListener,
    TodoAdapter.ToDoAdapterClicksInterface {
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private var popUpFragment: AddTodoPopupFragment? = null
    private lateinit var adapter: TodoAdapter
    private lateinit var mList: MutableList<ToDoData>
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var datee =""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        getDataFromFirebase()

        //used 1
        registerEvents()
        //Notification when Event occur
        createNotificationChannel()


    }

    private fun scheduleNotification(todotime: Long) {
        val intent = Intent(context, Notification::class.java)
        val title = "ToDo Task Due"
        val message = "Your Todo task is due"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = this.activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = todotime
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(context)

        val timeFormat = android.text.format.DateFormat.getTimeFormat(context)

        AlertDialog.Builder(context)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + "Task Alart" +
                        "\nMessage: " + "Notification Created" +
                        "\nAt: " + datee  +" "+ timeFormat.format(date)
            )
            .setPositiveButton("Okay") { _, _ -> }
            .show()
    }


    //Notification Class for Creating Notification
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager =
            this.activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    private fun registerEvents() {
        binding.addTask.setOnClickListener {
            if (popUpFragment != null)
            //to prevent multi pop up fragment
                childFragmentManager.beginTransaction().remove(popUpFragment!!)
            popUpFragment = AddTodoPopupFragment()
            popUpFragment!!.setListener(this)
            popUpFragment!!.show(
                childFragmentManager,
                AddTodoPopupFragment.TAG
            )
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("Tasks").child(auth.currentUser?.uid.toString())
        binding.recycleView.setHasFixedSize(true)
        binding.recycleView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        adapter = TodoAdapter(mList)
        adapter.setListener(this)
        binding.recycleView.adapter = adapter


    }

    private fun getDataFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for (taskSnapShot in snapshot.children) {
                    val todoTask = taskSnapShot.key?.let {
                        ToDoData(
                            it,
                            taskSnapShot.value.toString(),
                            snapshot.value.toString(),
                            snapshot.value.toString()
                        )
                    }
                    if (todoTask != null) {
                        mList.add(todoTask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onSaveTask(
        todo: String,
        todoDate: String,
        todoTime: String,
        todoET: TextInputEditText
    ) {
        val user = UserModel(todo, todoDate, todoTime)
        databaseRef.push().setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Task is been added", Toast.LENGTH_LONG).show()
                //todo sending selected time to alarm notification
                datee=todoDate
                val time = todoDate +" "+ todoTime
                val format = SimpleDateFormat("dd:MM:yyyy HH:mm")
                val date = format.parse(time)
                val timestamp = date.time
                scheduleNotification(timestamp)

            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
            todoET.text = null
            popUpFragment!!.dismiss()
        }

    }

    override fun onUpdateTask(
        toDoData: ToDoData,
        todoTask: String,
        todoDate: String,
        todoTime: String,
        todoET: TextInputEditText
    ) {
        //firebase update only works with map
        val map1 = mapOf<String, Any>(
            "todoDate" to todoDate,
            "todoTime" to todoTime,
            "todoTask" to todoTask
        )
        val string = toDoData.taskId
        val ma = HashMap<String, Any>()
        ma[string] = map1

        databaseRef.updateChildren(ma).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Updated  Successfully", Toast.LENGTH_LONG).show()
                //todo sending selected time on update to alarm notification
                datee=todoDate
                val time = todoDate +" "+ todoTime
//                val format = SimpleDateFormat("HH:mm", Locale.getDefault())
                val format = SimpleDateFormat("dd:MM:yyy HH:mm")
                val date = format.parse(time)
                val timestamp = date.time
                scheduleNotification(timestamp)
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
            popUpFragment!!.dismiss()
        }
    }

    override fun onDeleteTaskBtnCicked(toDoData: ToDoData) {
        databaseRef.child(toDoData.taskId).removeValue().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onEditTaskBtnCicked(toDoData: ToDoData) {
        if (popUpFragment != null)
            childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()

        popUpFragment = AddTodoPopupFragment.newInstance(toDoData.taskId, toDoData.task)
        popUpFragment!!.setListener(this)
        popUpFragment!!.show(childFragmentManager, AddTodoPopupFragment.TAG)
    }
}