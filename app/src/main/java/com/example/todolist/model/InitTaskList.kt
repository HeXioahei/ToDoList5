package com.example.todolist.model

import com.example.todolist.data.Item

class InitTaskList {
    fun initUnfinishedTaskList(): MutableList<Item> {
        val task = Item(0, "快来创建你的第一个任务吧", "", "0", "0", "0")
        return mutableListOf(task)
    }

    fun initFinishedTaskList(): MutableList<Item> {
        val task = Item(0, "还没有完成的任务哦", "继续加油", "1", "0", "0")
        return mutableListOf(task)
    }

    fun initList(): MutableList<Item> {
        val task = Item(0, "点击【未完成】或【已完成】", "来查看您的任务列表吧aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "0", "0", "0")
        return mutableListOf(task)
    }
}