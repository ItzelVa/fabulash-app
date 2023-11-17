package com.itzel.fabulash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.itzel.fabulash.models.Resena
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            val categoria = findViewById<AutoCompleteTextView>(R.id.filterBarOptions)
            val opcion = findViewById<AutoCompleteTextView>(R.id.filterBarServoPer)
            val estrellas = findViewById<RatingBar>(R.id.ratingBar)
            val comentario = findViewById<EditText>(R.id.editTextTextMultiLine)

            val newReview = Resena(
                categoria = categoria.text.toString(),
                destino = opcion.text.toString(),
                estrellas = estrellas.rating.toInt(),
                comentario = comentario.text.toString()
            )

            Api.request.postReview(newReview).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@NewReview, "Gracias por dejar una reseña", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@NewReview, Home::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@NewReview, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@NewReview, "Fallo en la api", Toast.LENGTH_SHORT).show()
                }

            })


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