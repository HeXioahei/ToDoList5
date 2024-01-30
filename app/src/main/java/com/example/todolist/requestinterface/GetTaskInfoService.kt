package com.example.todolist.requestinterface

import com.example.todolist.data.GetInfoData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetTaskInfoService {
    @GET("/task")
    fun getTaskInfo(@Query("status") status: String,
                    @Header("Authorization") Authorization: String)
    : Call<GetInfoData>
}