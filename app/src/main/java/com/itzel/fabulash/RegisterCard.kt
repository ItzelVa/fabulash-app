package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class RegisterCard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_card)

        val registerBack = findViewById<ImageButton>(R.id.cardRegisterBackButton)
        registerBack.setOnClickListener{
            val view = Intent(this,Account::class.java)
            startActivity(view)
        }
    }
}