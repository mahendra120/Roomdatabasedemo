package com.example.roomdatabasedemo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {

        private var instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            if (instance != null) {
                return instance!!
            }

            instance = Room.databaseBuilder(context = context, UserDatabase::class.java, "MyApp")
                .build()
            return instance!!

        }
    }

}