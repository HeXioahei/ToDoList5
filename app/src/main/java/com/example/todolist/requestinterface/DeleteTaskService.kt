package com.example.todolist.requestinterface

import com.example.todolist.data.BaseData
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteTaskService {
    @DELETE("/task/{id}")
    fun deleteTask(@Path("id") id: String,
                   @Header("Authorization") Authorization: String)
    : Call<BaseData>
}