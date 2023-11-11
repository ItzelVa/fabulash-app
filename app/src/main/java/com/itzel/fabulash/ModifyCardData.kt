package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.itzel.fabulash.databinding.ActivityModifyCardBinding
import com.itzel.fabulash.databinding.ActivityModifyCardDataBinding
import java.lang.Integer.parseInt

class ModifyCardData : AppCompatActivity() {
    private var pos: Int = 0
    private lateinit var binding: ActivityModifyCardDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyCardDataBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_modify_card_data)

        val receivedMessage = intent.getIntExtra("Position", 0)

        // Assuming you have a TextView with ID "textViewMessage" in your layout
        pos = receivedMessage
        val presentCard = getCards()
        val presentCardData = presentCard[pos]
        val button = findViewById<Button>(R.id.editCardButton)
        val nombre = findViewById<TextInputEditText>(R.id.nombreTitularM)
        val numero = findViewById<TextInputEditText>(R.id.numeroTarjetaM)
        val fecha = findViewById<TextInputEditText>(R.id.fechaTarjetaM)
        val cvc = findViewById<TextInputEditText>(R.id.cvcTarjetaM)

        nombre.setText(presentCardData.name)
        numero.setText(presentCardData.number)
        fecha.setText(presentCardData.date)
        cvc.setText(presentCardData.cvc)

        val backMod = findViewById<ImageButton>(R.id.cardModifyDataBackButton)
        backMod.setOnClickListener {
            val intent = Intent(this,ModifyCard::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            Toast.makeText(this, "Tarjeta Modificada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ModifyCard::class.java)
            startActivity(intent)
        }
    }

    private fun getCards(): MutableList<Cards> {
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
}