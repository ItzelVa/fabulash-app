package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.itzel.fabulash.adapter.ViewCardsAdapter
import com.itzel.fabulash.databinding.ActivityChoosePaymentBinding
import com.itzel.fabulash.models.AppointmentPost
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChoosePayment : AppCompatActivity() {

    private lateinit var binding: ActivityChoosePaymentBinding
    private val cards = arrayOf("")
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesSession: SharedPreferences
    private lateinit var chosenService: String
    private lateinit var chosenLashes: String
    private lateinit var chosenEmployee: String
    private lateinit var chosenDate: String
    private lateinit var chosenHour: String
    private var idClient: Int = 0
    private var idService: Int = 0
    private var idEmployee: Int = 0
    private var idLashes: Int? = 0
    private var lashesPrice: Float = 0f
    private var servicePrice: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoosePaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesSession = getSharedPreferences("session", Context.MODE_PRIVATE)
        sharedPreferences = getSharedPreferences("service", Context.MODE_PRIVATE)
        chosenService = sharedPreferences.getString("name_service", "")!!
        chosenLashes = sharedPreferences.getString("name_lashes", "No aplica")!!
        chosenEmployee = sharedPreferences.getString("name_employee", "")!!
        chosenDate = sharedPreferences.getString("chosenDate", "")!!
        chosenHour = sharedPreferences.getString("chosenHour", "")!!

        getServicePrice()

        val arrayAdapterCards = ArrayAdapter(this,R.layout.dropdown_filter_lashes,cards)

        binding.cardBar.setAdapter(arrayAdapterCards)

        getCards()

        binding.serviceChosen.text = chosenService
        binding.lashesChosen.text = chosenLashes
        binding.employeeChosen.text = chosenEmployee
        binding.dateChosen.text = chosenDate
        binding.hourChosen.text = chosenHour
        binding.totalPayment.text = "$${getServicePrice()}"


    }

    override fun onStart() {
        super.onStart()
        binding.choosePaymentBackButton.setOnClickListener {
            val intent = Intent(this,ChooseAppointment::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        // Botón confirmar
        binding.confirmButton.setOnClickListener {
            if(!(binding.cardBar.text.isBlank()) and !(binding.cardBar.text.contains("Escoja una tarjeta"))) {
                Api.request.setAppoiment(
                    AppointmentPost(
                        clvfp = 1,
                        clvpes = idLashes,
                        clvser = idService,
                        clvstat = 1,
                        clvusu = idClient,
                        fecha = chosenDate,
                        hab = true,
                        hora = chosenHour,
                        precio_final = getServicePrice(),
                    ))
                    .enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful){
                                intent = Intent(this@ChoosePayment,ConfirmedAppointment::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finishAffinity()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@ChoosePayment, "Fallo", Toast.LENGTH_SHORT).show()
                        }

                    })
            }
        }

        // Botón cancelar
        binding.cancelButton.setOnClickListener {
            val intent = Intent(this,Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    private fun getServicePrice(): Float{
        lashesPrice = sharedPreferences.getFloat("price_lashes", 0f)
        servicePrice = sharedPreferences.getFloat("price_service", 0f)
        idService = sharedPreferences.getInt("id_service", 0)
        idLashes = sharedPreferences.getInt("id_lashes", 0)
        idEmployee = sharedPreferences.getInt("id_employee", 0)
        idClient = sharedPreferencesSession.getInt("id_user", 0)

        return lashesPrice + servicePrice
    }

    private fun getCards(){
        Api.request.getCardsInfo(idClient).enqueue(object : Callback<MutableList<Cards>> {
            override fun onResponse(
                call: Call<MutableList<Cards>>,
                response: Response<MutableList<Cards>>
            ) {
                if (response.isSuccessful){
                    val data = mutableListOf<String>()

                    for (card in response.body()!!){
                        data.add(card.numero)
                    }

                    val arrayAdapterCards = ArrayAdapter(this@ChoosePayment,R.layout.dropdown_filter_lashes,data)
                    binding.cardBar.setAdapter(arrayAdapterCards)
                }
            }

            override fun onFailure(call: Call<MutableList<Cards>>, t: Throwable) {
                Toast.makeText(this@ChoosePayment, "Fallo en la api", Toast.LENGTH_SHORT).show()
            }
        })

    }
}