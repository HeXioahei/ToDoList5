package com.example.todolist.requestinterface

import com.example.todolist.data.LoginData
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface UserLoginService {
    @POST("/user/login/")
    fun userLogin(@Query("username") username: String, @Query("password") password: String)
    : Call<LoginData>
}