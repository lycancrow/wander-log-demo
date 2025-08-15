package com.example.bayonefelipecuervoasset.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bayonefelipecuervoasset.database.dbModel.ToDoDbModel

@Dao
interface ToDoDao {

    //Insert Data in the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserData(insertTag: ToDoDbModel)

    //get All Data from the database
    @Query("SELECT * FROM task_to_do")
    fun getAllUserData(): LiveData<List<ToDoDbModel>>

    //get All Data from the database
    @Query("SELECT * FROM task_to_do")
    suspend fun refreshTasks(): List<ToDoDbModel>

    @Query("SELECT * FROM task_to_do ORDER BY id DESC LIMIT 1")
    fun getMostRecentUserData(): LiveData<ToDoDbModel>

    //delete Data from the database using id
    @Query("DELETE FROM task_to_do WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int?)

}