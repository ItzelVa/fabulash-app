package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner

class NewReview : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)

        val spinner = findViewById<Spinner>(R.id.spinner1)
        val spinner2 = findViewById<Spinner>(R.id.spinner2)

        val data = listOf("Servicio", "Empleado/Empleada",)
        val data2 = listOf("Carmen Perez Diaz", "Fulano Fulanito",)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, data2)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner2.adapter = adapter2

        val back = findViewById<ImageButton>(R.id.newReviewBackButton)
        back.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}