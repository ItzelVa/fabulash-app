package com.itzel.fabulash

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewReview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)

        val add = findViewById<Button>(R.id.addReviewButton)
        val spinner = findViewById<AutoCompleteTextView>(R.id.filterBarOptions)
        val spinner2 = findViewById<AutoCompleteTextView>(R.id.filterBarServoPer)

        val data = arrayOf("Servicio", "Empleado/Empleada")
        val data2 = arrayOf("Carmen Perez Diaz", "Fulano Fulanito")
        val adapter = ArrayAdapter(this, R.layout.dropdown_filter_lashes, data)
        val adapter2 = ArrayAdapter(this, R.layout.dropdown_filter_lashes, data2)

        spinner.setAdapter(adapter)
        spinner2.setAdapter(adapter2)

        add.setOnClickListener {
            Toast.makeText(this, "Gracias por dejar una reseña", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        val back = findViewById<ImageButton>(R.id.newReviewBackButton)
        back.setOnClickListener {
            val intent = Intent(this, Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}