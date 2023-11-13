package com.itzel.fabulash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapters.ViewCardsAdapter
import com.itzel.fabulash.databinding.TarjetasBinding
import com.itzel.fabulash.events.OnClickListenerCards

class ViewCards : AppCompatActivity(), OnClickListenerCards {
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: TarjetasBinding
    private lateinit var cardAdapter: ViewCardsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TarjetasBinding.inflate(layoutInflater)

        setContentView(binding.root)
        cardAdapter = ViewCardsAdapter(getCards(),this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerTarjetas.apply {
            layoutManager = linearLayoutManager
            adapter = cardAdapter
        }

        val addCard = findViewById<Button>(R.id.addTarjeta)
        addCard.setOnClickListener {
            val view = Intent(this,RegisterCard::class.java)
            startActivity(view)
        }

        val deleteCard = findViewById<Button>(R.id.deleteTarjeta)
        deleteCard.setOnClickListener {
            val view = Intent(this,DeleteCard::class.java)
            startActivity(view)
        }

        val modifyCard = findViewById<Button>(R.id.modifyTarjeta)
        modifyCard.setOnClickListener {
            val view = Intent(this,ModifyCard::class.java)
            startActivity(view)
        }

        val backCard = findViewById<ImageButton>(R.id.tarjetasBackButton)
        backCard.setOnClickListener{
            val view = Intent(this,Account::class.java)
            startActivity(view)
        }
    }



    private fun getCards(): MutableList<Cards>{
        val cards = mutableListOf<Cards>()
        val card1 = Cards("Itzel Ochoa","400 100 233 1923","11/25","341")
        val card2 = Cards("Itzel Ochoa","510 100 233 1923","11/25","300")
        val card3 = Cards("Itzel Ochoa","654 100 233 1923","11/25","300")
        val card4 = Cards("Itzel Ochoa","644 100 233 1923","11/25","300")

        cards.add(card1)
        cards.add(card2)
        cards.add(card3)
        cards.add(card4)

        return cards
    }

    override fun onClick(card: Cards, position: Int) {
        TODO("Not yet implemented")
    }
}