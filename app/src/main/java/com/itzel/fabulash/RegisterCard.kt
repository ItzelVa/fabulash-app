package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterCard : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_card)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val registerBack = findViewById<ImageButton>(R.id.cardRegisterBackButton)
        registerBack.setOnClickListener{

            val view = Intent(this@RegisterCard,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        val addButton = findViewById<Button>(R.id.addCardButton)
        addButton.setOnClickListener{
            val idCliente = sharedPreferences.getInt("id_user", 0)
            val nombre = findViewById<TextInputEditText>(R.id.nombreTitular)
            val apellido = findViewById<TextInputEditText>(R.id.apellidoTitular)
            val numero = findViewById<TextInputEditText>(R.id.numeroTarjeta)
            val fecha = findViewById<TextInputEditText>(R.id.fechaTarjeta)
            val cvc = findViewById<TextInputEditText>(R.id.cvcTarjeta)

            val newCard = Cards(
                apellido = apellido.text.toString(),
                clvusu = idCliente,
                cvd = cvc.text.toString().toInt(),
                fecha_vencimiento = fecha.text.toString(),
                nombre = nombre.text.toString(),
                numero = numero.text.toString()
            )

            Api.request.createCard(newCard).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@RegisterCard, "Tarjeta añadida", Toast.LENGTH_SHORT).show()
                        val view = Intent(this@RegisterCard,Account::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(view)
                        finish()
                    } else {
                        Toast.makeText(this@RegisterCard, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@RegisterCard, "Falló la api", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}