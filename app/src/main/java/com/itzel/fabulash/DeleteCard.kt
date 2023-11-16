package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    lateinit var cardNumber: String
    var pos: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteCardBinding.inflate(layoutInflater)
        tarjeta = TarjetasCardsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

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
        val idCliente = sharedPreferences.getInt("id_user", 0)
        var cards = mutableListOf<Cards>()

        Api.request.getCardsInfo(idCliente).enqueue(object : Callback<MutableList<Cards>> {
            override fun onResponse(
                call: Call<MutableList<Cards>>,
                response: Response<MutableList<Cards>>
            ) {
                cards = response.body()!!
            }

            override fun onFailure(call: Call<MutableList<Cards>>, t: Throwable) {
                Toast.makeText(this@DeleteCard, "Fallo en la api", Toast.LENGTH_SHORT).show()
            }
        })

        return cards
    }

    @SuppressLint("WrongViewCast", "ResourceAsColor")
    override fun onClick(card: Cards, position: Int) {
        cardNumber = card.numero
        pos = position
//        Toast.makeText(this, cardNumber, Toast.LENGTH_SHORT).show()
        val deleteButton = findViewById<Button>(R.id.deleteCardButton)
        deleteButton.isClickable = true
        deleteButton.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
    }

}