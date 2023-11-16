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
import com.itzel.fabulash.adapter.ModifyCardsAdapter
import com.itzel.fabulash.databinding.ActivityModifyCardBinding
import com.itzel.fabulash.databinding.TarjetasCardsBinding
import com.itzel.fabulash.events.OnClickListenerModifyCards
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyCard : AppCompatActivity(), OnClickListenerModifyCards {
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityModifyCardBinding
    private lateinit var cardAdapter: ModifyCardsAdapter
    private lateinit var tarjeta: TarjetasCardsBinding
    private lateinit var sharedPreferences : SharedPreferences
    private lateinit var rec: RecyclerView
    var pos: Int = 0
    private lateinit var cardToSend: Cards
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyCardBinding.inflate(layoutInflater)
        tarjeta = TarjetasCardsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_modify_card)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        val modBackButton = findViewById<ImageButton>(R.id.modifyCardBackButton)
        modBackButton.setOnClickListener {
            val intent = Intent(this, Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        rec = findViewById(R.id.recyclerModifyTarjetas)

        val mcb = findViewById<Button>(R.id.modifyCardButton)
        mcb.setOnClickListener {
            Log.i("Hell","Not working")

            val intent = Intent(this, ModifyCardData::class.java)
            intent.putExtra("id_card", pos)
            startActivity(intent)

        }

        getCards()

    }
    private fun getCards(): MutableList<Cards> {
        val idCliente = sharedPreferences.getInt("id_user", 0)
        var cards = mutableListOf<Cards>()

        cardAdapter = ModifyCardsAdapter(cards, this)
        cardAdapter.setOnItemClickListener(this)
        linearLayoutManager = LinearLayoutManager(this)

        rec.apply {
            layoutManager = linearLayoutManager
            adapter = cardAdapter
        }

        Api.request.getCardsInfo(idCliente).enqueue(object : Callback<MutableList<Cards>> {
            override fun onResponse(
                call: Call<MutableList<Cards>>,
                response: Response<MutableList<Cards>>
            ) {
                cards = response.body()!!

                cardAdapter = ModifyCardsAdapter(cards, this@ModifyCard)
                cardAdapter.setOnItemClickListener(this@ModifyCard)
                linearLayoutManager = LinearLayoutManager(this@ModifyCard)

                rec.apply {
                    layoutManager = linearLayoutManager
                    adapter = cardAdapter
                }
            }

            override fun onFailure(call: Call<MutableList<Cards>>, t: Throwable) {
                Toast.makeText(this@ModifyCard, "Fallo en la api", Toast.LENGTH_SHORT).show()
            }
        })

        return cards
    }

    @SuppressLint("WrongViewCast", "ResourceAsColor")
    override fun onClick(card: Cards, position: Int) {
//        cardNumber = card.number
        pos = cardAdapter.getCardId(position)
        cardToSend = card
        Log.i("ID_CARD", "ID ${pos}")
//        Toast.makeText(this, "$card", Toast.LENGTH_SHORT).show()
        val modifyButton = findViewById<Button>(R.id.modifyCardButton)
        modifyButton.isClickable = true
        modifyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
    }


}


