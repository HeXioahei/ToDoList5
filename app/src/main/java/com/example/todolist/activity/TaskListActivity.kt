package com.example.todolist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.ListAdapter0
import com.example.todolist.adapter.ListAdapter1
import com.example.todolist.data.GetInfoData
import com.example.todolist.model.Item
import com.example.todolist.databinding.ActivityTaskListBinding
import com.example.todolist.model.InitTaskList
import com.example.todolist.requestinterface.GetTaskInfoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskListBinding
    private var taskList0: MutableList<Item> = InitTaskList().initUnfinishedTaskList()
    private var taskList1: MutableList<Item> = InitTaskList().initFinishedTaskList()
    private var initTaskList: MutableList<Item> = InitTaskList().initList()
    private lateinit var taskListAdapter0: ListAdapter0
    private lateinit var taskListAdapter1: ListAdapter1
    private lateinit var initTaskListAdapter: ListAdapter1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onTaskListActivity()
    }

    override fun onRestart() {
        super.onRestart()
        onTaskListActivity()
    }

    fun onTaskListActivity() {
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("token") as String

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        initTaskListAdapter = ListAdapter1(initTaskList, token,  this)
        binding.taskList.adapter = initTaskListAdapter

        binding.taskList.layoutManager = layoutManager

        val retrofit = Retrofit.Builder()
            .baseUrl("http://47.115.212.55:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val appService = retrofit.create(GetTaskInfoService::class.java)
        binding.unFinishedBtn.setOnClickListener {
            appService.getTaskInfo("0", "Bearer $token").enqueue(object : Callback<GetInfoData> {
                override fun onResponse(call: Call<GetInfoData>, response: Response<GetInfoData>) {
                    val currentActivity = getCurrentActivity() as TaskListActivity
                    val back = response.body()
                    if (back?.data != null) {
                        taskList0 = back.data.item
                    }
                    taskListAdapter0 = ListAdapter0(taskList0, token,  currentActivity)
                    binding.taskList.adapter = taskListAdapter0
                }
                override fun onFailure(call: Call<GetInfoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        binding.finishedBtn.setOnClickListener {
            appService.getTaskInfo("1", "Bearer $token").enqueue(object : Callback<GetInfoData> {
                override fun onResponse(call: Call<GetInfoData>, response: Response<GetInfoData>) {
                    val currentActivity = getCurrentActivity() as TaskListActivity
                    val back = response.body()
                    if (back?.data != null) {
                        taskList1 = back.data.item
                    }
                    taskListAdapter1 = ListAdapter1(taskList1, token,  currentActivity)
                    binding.taskList.adapter = taskListAdapter1
                }
                override fun onFailure(call: Call<GetInfoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }


        // 点击【添加】按钮，跳转到编辑页面，新建任务
        binding.addTaskBtn.setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("token", token)
            this.startActivity(intent)
        }
    }

    fun Activity.getCurrentActivity(): Activity {
        return this@getCurrentActivity
    }
}