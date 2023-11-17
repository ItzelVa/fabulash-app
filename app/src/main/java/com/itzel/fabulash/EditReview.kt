package com.itzel.fabulash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.RatingBar

class EditReview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_review)

        val intent = intent

        val comentario = findViewById<EditText>(R.id.editTextTextMultiLine)
        val estrellas = findViewById<RatingBar>(R.id.ratingBar)
        val destino = findViewById<AutoCompleteTextView>(R.id.filterBarServoPer)
        val categoria = findViewById<AutoCompleteTextView>(R.id.filterBarOptions)
        
    }
}