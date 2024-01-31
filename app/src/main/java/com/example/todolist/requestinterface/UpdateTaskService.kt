package com.example.todolist.requestinterface

import com.example.todolist.data.BaseData
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UpdateTaskService {
    @PUT("/task/{id}")
    fun updateTask(@Path("id") id: String,
                   @Header("Authorization") Authorization: String,
                   @Body status: FormBody): Call<BaseData>
}