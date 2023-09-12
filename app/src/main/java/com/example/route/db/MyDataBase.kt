package com.example.route.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.route.model.Task
import com.example.route.dao.UserDao

@Database(entities = [Task::class], version = 1 , exportSchema = true)
abstract class MyDataBase:RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{

        private  var instance:MyDataBase?=null
        fun getInstance(context: Context):MyDataBase{
            if(instance==null){
               instance = Room.databaseBuilder(context,
                    MyDataBase::class.java,
                   "tasksDB"
                   ).allowMainThreadQueries()
                   .fallbackToDestructiveMigration()
                   .build()
            }
            return instance!!
        }
    }

}