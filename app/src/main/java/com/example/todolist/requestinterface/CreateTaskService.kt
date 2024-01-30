package com.example.todolist.requestinterface

import com.example.todolist.data.BaseData
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CreateTaskService {
    @POST("/task")
    fun createTask(@Query("title") title: String,
                   @Query("content") content: String,
                   @Query("start_time") start_time: String,
                   @Query("end_time") end_time: String,
                   @Query("status") status: String,
                   @Header("Authorization") Authorization: String): Call<BaseData>
}