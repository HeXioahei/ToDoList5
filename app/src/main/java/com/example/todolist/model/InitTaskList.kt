package com.example.todolist.model

class InitTaskList {
    fun initUnfinishedTaskList(): MutableList<Item> {
        val task = Item(0, "还没有flag耶", "快来立一个伟大的flag吧！", "0", "现在", "未知")
        return mutableListOf(task)
    }

    fun initFinishedTaskList(): MutableList<Item> {
        val task = Item(0, "还没有实现的flag耶", "继续加油！flag只要不推翻， 就一定能实现！", "1", "未知", "未知")
        return mutableListOf(task)
    }

    fun initList(): MutableList<Item> {
        val task = Item(0, "欢迎来立flag！", "点击【未完成】或【已完成】来查看你的一个个伟大的flag吧", "0", "", "")
        return mutableListOf(task)
    }
}