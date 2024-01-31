package com.example.todolist.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.data.BaseData
import com.example.todolist.data.LoginData
import com.example.todolist.databinding.ActivityLoginBinding
import com.example.todolist.requestinterface.UserLoginService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 跳转到注册页面进行注册
        binding.registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // 进行登录
        binding.loginBtn.setOnClickListener {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://47.115.212.55:5000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(UserLoginService::class.java)

            val handler = Handler(Looper.getMainLooper())
            appService.userLogin(
                binding.loginUsername.text.toString(),
                binding.loginPassword.text.toString()
            ).enqueue(object : Callback<LoginData> {
                override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                    val currentAcitivity = getCurrentActivity()
                    val back = response.body()
                    val token = back?.token
                    val code = back?.code
                    if (code == 200) {
                        handler.post {
                            Toast.makeText(currentAcitivity, "登入成功", Toast.LENGTH_SHORT).show()
                        }
                        val intent = Intent(currentAcitivity, TaskListActivity::class.java)
                        intent.putExtra("token", token)
                        startActivity(intent)
                    } else {
                        handler.post {
                            Toast.makeText(currentAcitivity, "登入失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onFailure(call: Call<LoginData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    fun Activity.getCurrentActivity(): Activity {
        return this@getCurrentActivity
    }
}