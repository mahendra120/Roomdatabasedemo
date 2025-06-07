package com.example.roomdatabasedemo.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDAO {

    @Insert
    suspend fun addUser(user : User)

    @Query("select * from user")
    suspend fun getUsers() : List<User>

    @Update
    suspend fun updateUser(user : User)

    @Delete
    suspend fun deleteUser(user : User)

}