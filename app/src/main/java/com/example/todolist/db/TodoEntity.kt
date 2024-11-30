package com.example.todolist.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity (
    @PrimaryKey(autoGenerate = true) val id : Long? = null,
    @ColumnInfo(name="title") var title : String,
    @ColumnInfo(name="importance") var importance : Int,
    @ColumnInfo(name="description") var description: String
        ) {
}