package com.example.todolist


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User")
data class User (
    var title: String,
    var description: String,
   var category:String,
    var date:Long,
    var time:Long,
    var isFinished:Int= 0,
var isShow:Int=0,

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
)
