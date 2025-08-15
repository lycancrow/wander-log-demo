package com.example.wanderlogdemo.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.bayonefelipecuervoasset.database.dbModel.ToDoDbModel
import com.example.wanderlogdemo.R
import com.example.wanderlogdemo.databinding.ActivityAddNewNoteBinding
import com.example.wanderlogdemo.viewModel.AddNewNoteViewModel
import java.util.Calendar

class AddNewNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewNoteBinding
    private lateinit var viewModel: AddNewNoteViewModel
    private var currentUserEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(AddNewNoteViewModel::class.java)
        binding.etDate.setOnClickListener {showDatePicker()}
        viewModel.selectedDate.observe(this) { dateString ->
            binding.etDate.setText(dateString)
        }
        viewModel.userEmail.observe(this) { email -> currentUserEmail = email }
        viewModel.saveResult.observe(this) { event ->
            event.getContentIfNotHandled()?.let { success ->
                if (success) {
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "there was a mistake saving your task, please try again later",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.btnRegisterAccount.setOnClickListener { addNote() }

    }



    private fun addNote() {

        val userEmail = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email
        viewModel.addUserData(ToDoDbModel(
            id = null,
            title = binding.etEmail.text.toString(),
            date = binding.etDate.text.toString(),
            task = binding.etContent.text.toString(),
            email = userEmail ?: ""
        ))
    }

    private fun showDatePicker() {
        val cal = Calendar.getInstance()
        val existing = binding.etDate.text?.toString()
        val parsed = existing?.let { viewModel.parseMmDdYyyy(it) }
        if (parsed != null) {
            val (year, mm, dd) = parsed
            cal.set(year, mm - 1, dd)
        }

        val initialYear = cal.get(Calendar.YEAR)
        val initialMonth = cal.get(Calendar.MONTH)
        val initialDay = cal.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, year, monthOfYearZeroBased, dayOfMonth ->
                viewModel.setDate(year, monthOfYearZeroBased + 1, dayOfMonth)
            },
            initialYear,
            initialMonth,
            initialDay
        )

        dpd.show()
    }

}