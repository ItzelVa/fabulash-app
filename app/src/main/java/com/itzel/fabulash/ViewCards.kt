package com.itzel.fabulash

import android.content.Context
import com.itzel.fabulash.adapter.ViewCardsAdapter
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapter.ModifyCardsAdapter
import com.itzel.fabulash.databinding.TarjetasBinding
import com.itzel.fabulash.events.OnClickListenerCards
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewCards : AppCompatActivity(), OnClickListenerCards {
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: TarjetasBinding
    private lateinit var cardAdapter: ViewCardsAdapter
    private lateinit var sharedPreferences : SharedPreferences
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TarjetasBinding.inflate(layoutInflater)

        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val addCard = findViewById<Button>(R.id.addTarjeta)
        addCard.setOnClickListener {
            val view = Intent(this,RegisterCard::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        val deleteCard = findViewById<Button>(R.id.deleteTarjeta)
        deleteCard.setOnClickListener {
            val view = Intent(this,DeleteCard::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        val modifyCard = findViewById<Button>(R.id.modifyTarjeta)
        modifyCard.setOnClickListener {
            val view = Intent(this,ModifyCard::class.java)
                .putExtra("id_card", position)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        val backCard = findViewById<ImageButton>(R.id.tarjetasBackButton)
        backCard.setOnClickListener{
            val view = Intent(this,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        getCards()
    }



    private fun getCards(){
        val idCliente = sharedPreferences.getInt("id_user", 0)
        var cards = mutableListOf<Cards>()

        cardAdapter = ViewCardsAdapter(cards,this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerTarjetas.apply {
            layoutManager = linearLayoutManager
            adapter = cardAdapter
        }

        Api.request.getCardsInfo(idCliente).enqueue(object : Callback<MutableList<Cards>> {
            override fun onResponse(
                call: Call<MutableList<Cards>>,
                response: Response<MutableList<Cards>>
            ) {
                cards = response.body()!!

                cardAdapter = ViewCardsAdapter(cards,this@ViewCards)
                linearLayoutManager = LinearLayoutManager(this@ViewCards)

                binding.recyclerTarjetas.apply {
                    layoutManager = linearLayoutManager
                    adapter = cardAdapter
                }
            }

            override fun onFailure(call: Call<MutableList<Cards>>, t: Throwable) {
                Toast.makeText(this@ViewCards, "Fallo en la api", Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onClick(card: Cards, position: Int) {
        this.position = cardAdapter.getCardId(position)
        Log.i("CARD_POSITION", "Id de la tarjeta: ${position}")
    }
}