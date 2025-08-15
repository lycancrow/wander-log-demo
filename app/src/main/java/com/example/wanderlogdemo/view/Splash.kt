package com.example.wanderlogdemo.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wanderlogdemo.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSpash= installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Thread.sleep(800)
        screenSpash.setKeepOnScreenCondition{true}
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}