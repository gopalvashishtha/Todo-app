package com.example.todolist


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface Userdao {

    @Insert
    suspend fun insertTask(user : User): Long

    @Query("SELECT * FROM User where isFinished == 0")
    fun getTask(): LiveData<List<User>>

    @Query("Update User Set isFinished = 1 where id=:uid")
    fun FinishTask(uid:Long)

    @Query("UPDATE User Set isShow = :isShow  where id LIKE :id")
   fun isShownUpdate(id:Long , isShow : Int)

    @Query("SELECT * from User where id Like :id")
    fun get(id : Long): User

    @Query("Delete from User  where id=:uid")
    fun deleteTask(uid:Long)



}