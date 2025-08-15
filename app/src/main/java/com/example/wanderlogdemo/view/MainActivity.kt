package com.example.wanderlogdemo.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wanderlogdemo.R
import com.example.wanderlogdemo.databinding.ActivityMainBinding
import com.example.wanderlogdemo.view.home.Home
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        mainView()
        binding.tvRegister.setOnClickListener{signUp()}
        binding.btnLogin.setOnClickListener {mainView()}

    }

    private fun mainView(){

        val email = binding.etEmail.text.toString()
        val pass = binding.etPassword.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty()) {

            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this, Home::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "User or password are not correct", Toast.LENGTH_SHORT).show()

                }
            }
        } else {
            Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

        }

    }
    private fun signUp(){
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }



}
