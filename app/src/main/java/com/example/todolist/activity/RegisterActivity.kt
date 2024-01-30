package com.example.todolist.activity

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.BaseData
import com.example.todolist.databinding.ActivityRegisterBinding
import com.example.todolist.requestinterface.UserRegisterService
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 完成注册并将数据上传到服务器进行保存
        binding.finishRegisterBtn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://47.115.212.55:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(UserRegisterService::class.java)
            val handler = Handler(Looper.getMainLooper())
            appService.userRegister(
                binding.registerUsername.text.toString(),
                binding.registerPassword.text.toString()
            ).enqueue(object : Callback<BaseData> {
                override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                    val currentActivity = getCurrentActivity()
                    val data = response.body()
                    if (data?.code == 200) {
                        handler.post {
                            Toast.makeText(currentActivity, "注册成功", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    } else {
                        handler.post {
                            Toast.makeText(currentActivity, "注册失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //code = data?.code
//                    Log.d("code", "${data?.code}")
//                    Log.d("RegisterActivity", "succeed")
//                    Log.d("Register", "$data")
//                    Log.d("rrrr", "${data?.code}")
//                    Log.d("a", "${data?.msg}")
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