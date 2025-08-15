package com.example.wanderlogdemo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bayonefelipecuervoasset.database.ToDoDatabase
import com.example.bayonefelipecuervoasset.database.dbModel.ToDoDbModel
import com.example.bayonefelipecuervoasset.database.dbRepository.ToDoDbRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale


class AddNewNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoDbRepository
    var mostRecentTask: LiveData<ToDoDbModel>



    private val _selectedDate = MutableLiveData<String>()
    private val auth = FirebaseAuth.getInstance()
    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> get() = _userEmail
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        _userEmail.value = firebaseAuth.currentUser?.email
    }

    // Event LiveData: true = success, false = failure
    private val _saveResult = MutableLiveData<Event<Boolean>>()
    val saveResult: LiveData<Event<Boolean>> get() = _saveResult

    init {
        // Database Initialization
        val dao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoDbRepository(dao)
        mostRecentTask = repository.getMostRecentUserDataLiveData()

        //Firebase get email
        _userEmail.value = auth.currentUser?.email
        auth.addAuthStateListener(authStateListener)
    }
    override fun onCleared() {
        super.onCleared()
        // Importante: quitar listener para evitar fugas
        auth.removeAuthStateListener(authStateListener)
    }

    //Add Element to Database
    fun addUserData(toDoDbModel: ToDoDbModel) = viewModelScope.launch(Dispatchers.IO) {
        try {
            repository.insertTask(toDoDbModel)
            // notify success
            _saveResult.postValue(Event(true))
        } catch (e: Exception) {
            // log opcional aquí
            _saveResult.postValue(Event(false))
        }
    }


    //Obtain Date
    val selectedDate: LiveData<String> get() = _selectedDate
    fun setDate(year: Int, month: Int, day: Int) {
        val formatted = String.format(Locale.US, "%02d/%02d/%04d", month, day, year)
        _selectedDate.value = formatted
    }
    fun parseMmDdYyyy(dateStr: String): Triple<Int, Int, Int>? {
        return try {
            val parts = dateStr.split("/")
            if (parts.size != 3) return null
            val mm = parts[0].toInt()
            val dd = parts[1].toInt()
            val yyyy = parts[2].toInt()
            Triple(yyyy, mm, dd)
        } catch (e: Exception) {
            null
        }
    }


}