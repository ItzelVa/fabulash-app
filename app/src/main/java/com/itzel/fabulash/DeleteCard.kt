package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itzel.fabulash.adapter.DeleteCardsAdapter
import com.itzel.fabulash.databinding.ActivityDeleteCardBinding
import com.itzel.fabulash.databinding.TarjetasCardsBinding
import com.itzel.fabulash.events.OnClickListenerDeleteCards
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteCard : AppCompatActivity(), OnClickListenerDeleteCards {

    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityDeleteCardBinding
    private lateinit var cardAdapter: DeleteCardsAdapter
    private lateinit var tarjeta: TarjetasCardsBinding
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var rec: RecyclerView
    lateinit var cardNumber: String
    var pos: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteCardBinding.inflate(layoutInflater)
        tarjeta = TarjetasCardsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        binding.deleteCardButton.setOnClickListener {
            MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
                .setTitle(R.string.deleteCard_question)
                .setMessage(cardNumber)
                .setNeutralButton(R.string.cancelar, {dialog, i -> })
                .setPositiveButton(R.string.eliminar, {dialog, i ->
                    Api.request.deleteCard(pos).enqueue(object : Callback<Void>{
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful){
                                Toast.makeText(this@DeleteCard, "Tarjeta elimanda", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@DeleteCard,Account::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@DeleteCard, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@DeleteCard, getString(R.string.error_en_la_api), Toast.LENGTH_SHORT).show()
                        }
                    })
                })
                .setCancelable(true)
                .show()
        }

        rec = findViewById(R.id.recyclerDeleteTarjetas)
        val delBackButton = findViewById<ImageButton>(R.id.deleteCardBackButton)

        delBackButton.setOnClickListener{
            val intent = Intent(this,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        getCards()
    }

    private fun getCards(){
        val idCliente = sharedPreferences.getInt("id_user", 0)
        var cards = mutableListOf<Cards>()

        cardAdapter = DeleteCardsAdapter(cards,this)
        cardAdapter.setOnItemClickListener(this)
        linearLayoutManager = LinearLayoutManager(this)

        rec.apply  {
            layoutManager = linearLayoutManager
            adapter = cardAdapter
        }

        Api.request.getCardsInfo(idCliente).enqueue(object : Callback<MutableList<Cards>> {
            override fun onResponse(
                call: Call<MutableList<Cards>>,
                response: Response<MutableList<Cards>>
            ) {
                cards = response.body()!!
                cardAdapter = DeleteCardsAdapter(cards,this@DeleteCard)
                cardAdapter.setOnItemClickListener(this@DeleteCard)
                linearLayoutManager = LinearLayoutManager(this@DeleteCard)

                rec.apply  {
                    layoutManager = linearLayoutManager
                    adapter = cardAdapter
                }
            }

            override fun onFailure(call: Call<MutableList<Cards>>, t: Throwable) {
                Toast.makeText(this@DeleteCard, "Fallo en la api", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("WrongViewCast", "ResourceAsColor")
    override fun onClick(card: Cards, position: Int) {
        pos = card.id!!
        cardNumber = card.numero
//        Toast.makeText(this, cardNumber, Toast.LENGTH_SHORT).show()
        val deleteButton = findViewById<Button>(R.id.deleteCardButton)
        deleteButton.isClickable = true
        deleteButton.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
    }

}