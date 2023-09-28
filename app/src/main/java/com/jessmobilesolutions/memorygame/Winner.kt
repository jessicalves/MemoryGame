package com.jessmobilesolutions.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jessmobilesolutions.memorygame.databinding.ActivityMainBinding

class Winner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val button: Button = findViewById(R.id.btnPlayAgain)

        button.setOnClickListener {
            Intent(this, MainActivity::class.java).run {
                startActivity(this)
            }
        }
    }
}