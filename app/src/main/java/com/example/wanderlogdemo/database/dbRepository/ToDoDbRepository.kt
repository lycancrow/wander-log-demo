package com.example.bayonefelipecuervoasset.database.dbRepository

import androidx.lifecycle.LiveData
import com.example.bayonefelipecuervoasset.database.ToDoDao
import com.example.bayonefelipecuervoasset.database.dbModel.ToDoDbModel


class ToDoDbRepository(private val toDoDao: ToDoDao) {
    private val allUserDataLiveData: LiveData<List<ToDoDbModel>> = toDoDao.getAllUserData()
    suspend fun insertTask(dietDbModel: ToDoDbModel){
        toDoDao.insertUserData(dietDbModel)
    }
    fun getMostRecentUserDataLiveData(): LiveData<ToDoDbModel> {
        return toDoDao.getMostRecentUserData()
    }

    fun getAllTasksLiveData(): LiveData<List<ToDoDbModel>> {
        return allUserDataLiveData
    }

    suspend fun refreshTasks() {
        toDoDao.refreshTasks()
    }

    suspend fun deleteTasks(id:Int){
        toDoDao.deleteTask(id)
    }

}