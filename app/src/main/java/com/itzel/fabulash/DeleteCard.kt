package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class DeleteCard : AppCompatActivity(), OnClickListenerDeleteCards {

    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityDeleteCardBinding
    private lateinit var cardAdapter: DeleteCardsAdapter
    private lateinit var tarjeta: TarjetasCardsBinding
    lateinit var cardNumber: String
    var pos: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteCardBinding.inflate(layoutInflater)
        tarjeta = TarjetasCardsBinding.inflate(layoutInflater)

        setContentView(binding.root)


        cardAdapter = DeleteCardsAdapter(getCards(),this)
        cardAdapter.setOnItemClickListener(this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.deleteCardButton.setOnClickListener {
            MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
                .setTitle(R.string.deleteCard_question)
                .setMessage(cardNumber)
                .setNeutralButton(R.string.cancelar, {dialog, i -> })
                .setPositiveButton(R.string.eliminar, {dialog, i ->
                    cardAdapter.remove(pos)
                    Toast.makeText(this, R.string.tarjetaEliminada, Toast.LENGTH_SHORT).show()
                })
                .setCancelable(true)
                .show()
        }

        val rec = findViewById<RecyclerView>(R.id.recyclerDeleteTarjetas)
        val delBackButton = findViewById<ImageButton>(R.id.deleteCardBackButton)

        delBackButton.setOnClickListener{
            val intent = Intent(this,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }


        rec.apply  {
            layoutManager = linearLayoutManager
            adapter = cardAdapter
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

    @SuppressLint("WrongViewCast", "ResourceAsColor")
    override fun onClick(card: Cards, position: Int) {
        cardNumber = card.number
        pos = position
//        Toast.makeText(this, cardNumber, Toast.LENGTH_SHORT).show()
        val deleteButton = findViewById<Button>(R.id.deleteCardButton)
        deleteButton.isClickable = true
        deleteButton.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
    }

}