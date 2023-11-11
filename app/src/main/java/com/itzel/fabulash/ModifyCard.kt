package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Intent
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
import com.itzel.fabulash.databinding.ActivityDeleteCardBinding
import com.itzel.fabulash.databinding.ActivityModifyCardBinding
import com.itzel.fabulash.databinding.TarjetasCardsBinding

class ModifyCard : AppCompatActivity(),OnClickListenerModifyCards {
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityModifyCardBinding
    private lateinit var cardAdapter: ModifyCardsAdapter
    private lateinit var tarjeta: TarjetasCardsBinding
    var pos: Int = 0
    private lateinit var cardToSend: Cards
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyCardBinding.inflate(layoutInflater)
        tarjeta = TarjetasCardsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_modify_card)

        cardAdapter = ModifyCardsAdapter(getCards(), this)
        cardAdapter.setOnItemClickListener(this)
        linearLayoutManager = LinearLayoutManager(this)

        val modBackButton = findViewById<ImageButton>(R.id.modifyCardBackButton)
        modBackButton.setOnClickListener {
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }

        val rec = findViewById<RecyclerView>(R.id.recyclerModifyTarjetas)
        rec.apply {
            layoutManager = linearLayoutManager
            adapter = cardAdapter
        }

        val mcb = findViewById<Button>(R.id.modifyCardButton)
        mcb.setOnClickListener {
            Log.i("Hell","Not working")

            val intent = Intent(this, ModifyCardData::class.java)
            intent.putExtra("Position", pos)
            startActivity(intent)
        }

    }
    fun getCards(): MutableList<Cards> {
        val cards = mutableListOf<Cards>()
        val card1 = Cards("Itzel Ochoa", "400 100 233 1923", "11/25", "341")
        val card2 = Cards("Itzel Ochoa", "510 100 233 1923", "11/25", "300")
        val card3 = Cards("Itzel Ochoa", "654 100 233 1923", "11/25", "300")
        val card4 = Cards("Itzel Ochoa", "644 100 233 1923", "11/25", "300")

        cards.add(card1)
        cards.add(card2)
        cards.add(card3)
        cards.add(card4)

        return cards
    }

    @SuppressLint("WrongViewCast", "ResourceAsColor")
    override fun onClick(card: Cards, position: Int) {
//        cardNumber = card.number
        pos = position
        cardToSend = card
//        Toast.makeText(this, "$card", Toast.LENGTH_SHORT).show()
        val modifyButton = findViewById<Button>(R.id.modifyCardButton)
        modifyButton.isClickable = true
        modifyButton.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
    }


}


