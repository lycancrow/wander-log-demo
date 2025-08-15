package com.example.bayonefelipecuervoasset.database.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName = "task_to_do")
data class ToDoDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "email")
    val email : String,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "date")
    val date : String,
    @ColumnInfo(name = "task")
    val task : String


)

