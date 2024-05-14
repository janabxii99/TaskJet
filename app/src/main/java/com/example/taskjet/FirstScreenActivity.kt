package com.example.taskjet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class FirstScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        supportActionBar?.hide()

        val intent = Intent(this@FirstScreenActivity, MainActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 2000)
    }
}
