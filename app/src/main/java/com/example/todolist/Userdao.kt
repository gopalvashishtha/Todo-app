package com.example.todolist


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface Userdao {

    @Insert
    suspend fun insertTask(user : User): Long

    @Query("SELECT * FROM User where isFinished != -1")
    fun getTask(): LiveData<List<User>>

    @Query("Update User Set isFinished = 1 where id=:uid")
    fun FinishTask(uid:Long)

    @Query("Delete from User  where id=:uid")
    fun deleteTask(uid:Long)



}