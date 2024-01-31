package com.example.todolist.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.activity.TaskListActivity
import com.example.todolist.data.BaseData
import com.example.todolist.model.Item
import com.example.todolist.requestinterface.DeleteTaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListAdapter1 (var taskList: MutableList<Item>,
                    val token: String,
                    val activity: TaskListActivity)
    : RecyclerView.Adapter<ListAdapter1.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskTitle: TextView = view.findViewById(R.id.taskTitle1)
        val time: TextView = view.findViewById(R.id.time1)
        val content: TextView = view.findViewById(R.id.content1)
        val deleteBtn: ImageView = view.findViewById(R.id.deleteBtn1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_item_finished, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://47.115.212.55:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val handler = Handler(Looper.getMainLooper())

        val task = taskList[position]
        holder.taskTitle.text = task.title
        holder.time.text = "时间：${task.start_time}-${task.end_time}"
        holder.content.text = task.content

        holder.deleteBtn.setOnClickListener {
            val appService = retrofit.create(DeleteTaskService::class.java)
            appService.deleteTask(taskList[position].tid.toString(), "Bearer $token").enqueue(object :
                Callback<BaseData> {
                override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                    val back = response.body()
                    val code = back?.code
                    if (code == 200) {
                        handler.post {
                            Toast.makeText(activity, "删除成功", Toast.LENGTH_SHORT).show()
                            activity.recreate()
                        }
                    } else {
                        handler.post {
                            Toast.makeText(activity, "删除失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onFailure(call: Call<BaseData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}