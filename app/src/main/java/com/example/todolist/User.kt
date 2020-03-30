package com.example.todolist


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User (
    var title: String,
    var description: String,
   var category:String,
    var date:Long,
    var time:Long,
    var isFinished:Int= -1,

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
)
