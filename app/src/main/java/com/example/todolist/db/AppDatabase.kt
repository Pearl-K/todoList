package com.example.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TodoEntity::class), version = 1) // 조건 1
abstract class AppDatabase : RoomDatabase() { // 조건 2

    abstract fun getTodoDao() : TodoDao // 조건 3

    companion object { //싱글톤 패턴
        val databaseName = "db_todo" //db 이름
        var appDatabase : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if(appDatabase == null){
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, databaseName).
                fallbackToDestructiveMigration().build()
            }
            return appDatabase
        }
    }

}