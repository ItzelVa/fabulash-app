package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.itzel.fabulash.databinding.ActivityModifyCardDataBinding
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyCardData : AppCompatActivity() {
    private var pos: Int = 0
    private lateinit var binding: ActivityModifyCardDataBinding
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyCardDataBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_modify_card_data)

        pos = intent.getIntExtra("id_card", 0)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val button = findViewById<Button>(R.id.editCardButton)

        val backMod = findViewById<ImageButton>(R.id.cardModifyDataBackButton)
        backMod.setOnClickListener {
            val intent = Intent(this,ModifyCard::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        button.setOnClickListener {
            val idCliente = sharedPreferences.getInt("id_user", 0)
            val nombre = findViewById<TextInputEditText>(R.id.nombreTitularM)
            val apellido = findViewById<TextInputEditText>(R.id.apellidoTitularM)
            val numero = findViewById<TextInputEditText>(R.id.numeroTarjetaM)
            val fecha = findViewById<TextInputEditText>(R.id.fechaTarjetaM)
            val cvc = findViewById<TextInputEditText>(R.id.cvcTarjetaM)
            val hab = true

            val newCardInfo = Cards(
                apellido = apellido.text.toString(),
                clvusu = idCliente,
                cvd = cvc.text.toString().toInt(),
                fecha_vencimiento = fecha.text.toString() + "-01",
                hab = hab,
                id = pos,
                nombre = nombre.text.toString(),
                numero = numero.text.toString()
            )

            Api.request.updateCardInfo(pos, newCardInfo).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@ModifyCardData, "Tarjeta Modificada", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ModifyCardData,ModifyCard::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@ModifyCardData, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@ModifyCardData, "Fallo la api", Toast.LENGTH_SHORT).show()
                }

            })


        }
        
        getCardInfo()
    }

    private fun getCardInfo() {
        val idCliente = sharedPreferences.getInt("id_user", 0)

        setCardInfo(Cards())

        Api.request.getCardInfo(idCliente, pos).enqueue(object : Callback<Cards>{
            override fun onResponse(call: Call<Cards>, response: Response<Cards>) {
                setCardInfo(response.body()!!)
            }

            override fun onFailure(call: Call<Cards>, t: Throwable) {
                Toast.makeText(this@ModifyCardData, "Fallo la api", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setCardInfo(presentCardData: Cards) {
        val nombre = findViewById<TextInputEditText>(R.id.nombreTitularM)
        val apellido = findViewById<TextInputEditText>(R.id.apellidoTitularM)
        val numero = findViewById<TextInputEditText>(R.id.numeroTarjetaM)
        val fecha = findViewById<TextInputEditText>(R.id.fechaTarjetaM)
        val cvc = findViewById<TextInputEditText>(R.id.cvcTarjetaM)

        nombre.setText(presentCardData.nombre)
        apellido.setText(presentCardData.apellido)
        numero.setText(presentCardData.numero)
        fecha.setText(presentCardData.fecha_vencimiento)
        Log.i("Error-tipo", "Valor de cvc ${presentCardData.cvd}")
        cvc.setText(presentCardData.cvd.toString())
    }
}