package com.example.route.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("tasks")
data class Task(
    @PrimaryKey
    var id:Int?=null,
    @ColumnInfo
    var title:String?=null,
    @ColumnInfo
    var description:String?=null,
    @ColumnInfo
    var dataTime:Long?=null,
    @ColumnInfo
    var isDone:Boolean?=null
):Serializable
