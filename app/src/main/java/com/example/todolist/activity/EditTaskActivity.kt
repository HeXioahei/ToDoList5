package com.example.todolist.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.BaseData
import com.example.todolist.databinding.ActivityEditTaskBinding
import com.example.todolist.requestinterface.CreateTaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("token") as String
        // 完成任务的创建，并将数据上传到服务器
        binding.onCreateTaskBtn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://47.115.212.55:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(CreateTaskService::class.java)
            val handler = Handler(Looper.getMainLooper())
            appService.createTask(
                binding.editTitle.text.toString(),
                binding.editContent.text.toString(),
                binding.editStartTime.text.toString(),
                binding.editEndTime.text.toString(),
                "0",
                "Bearer $token"
            ).enqueue(object : Callback<BaseData> {
                override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                    val currentActivity = getCurrentActivity()
                    val back = response.body()
                    val code = back?.code
//                    Log.d("content", "${binding.editContent.text}")
//                    Log.d("create", "${back?.code}")
                    if (code == 200) {
                        handler.post {
                            Toast.makeText(currentActivity, "添加成功", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    } else {
                        handler.post{
                            Toast.makeText(currentActivity, "添加失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onFailure(call: Call<BaseData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    fun Activity.getCurrentActivity(): Activity {
        return this@getCurrentActivity
    }
}