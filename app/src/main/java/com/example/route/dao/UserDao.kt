package com.example.route.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.route.model.Task

@Dao
interface UserDao {

    @Insert
    fun insertTask(task: Task)
    @Delete
    fun deleteTask(task: Task)
    @Update
    fun updateTask(task: Task)
    @Query("select * from tasks")
    fun getAllTasks():List<Task>
    @Query("select * from tasks where dataTime == :dateTime ")
    fun getTasksByDate(dateTime:Long):List<Task>
}