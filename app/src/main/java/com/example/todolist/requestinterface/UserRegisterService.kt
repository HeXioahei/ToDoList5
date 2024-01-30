package com.example.todolist.requestinterface

import com.example.todolist.data.BaseData
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRegisterService {
    @POST("/user/register/")
    fun userRegister(
        @Query("username") username: String,
        @Query("password") password: String
    ): Call<BaseData>
}