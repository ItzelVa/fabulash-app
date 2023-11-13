package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.itzel.fabulash.databinding.ActivityChoosePaymentBinding

class ChoosePayment : AppCompatActivity() {

    private lateinit var binding: ActivityChoosePaymentBinding
    private val cards = arrayOf("5467 7657 1432 9087", "8760 5305 1732 3047","3948 6547 0274 2684")
    private var ban = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoosePaymentBinding.inflate(layoutInflater)


        val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val chosenService = sharedPreferences.getString("chosenService", "")
        val chosenLashes = sharedPreferences.getString("chosenLashes", "No aplica")
        val chosenEmployee = sharedPreferences.getString("chosenEmployee", "")
        val chosenDate = sharedPreferences.getString("chosenDate", "")
        val chosenHour = sharedPreferences.getString("chosenHour", "")

        val arrayAdapterCards = ArrayAdapter(this,R.layout.dropdown_filter_lashes,cards)

        binding.cardBar.setAdapter(arrayAdapterCards)
        binding.serviceChosen.text = chosenService
        binding.lashesChosen.text = chosenLashes
        binding.employeeChosen.text = chosenEmployee
        binding.dateChosen.text = chosenDate
        binding.hourChosen.text = chosenHour
        binding.totalPayment.text = "$500.00"

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.choosePaymentBackButton.setOnClickListener {
            val intent = Intent(this,ChooseAppointment::class.java)
            startActivity(intent)
        }

        // Botón confirmar
        binding.confirmButton.setOnClickListener {
            if(!(binding.cardBar.text.isBlank()) and !(binding.cardBar.text.contains("Escoja una tarjeta"))){
                intent = Intent(this,ConfirmedAppointment::class.java)
                startActivity(intent)
            }
        }

        // Botón cancelar
        binding.cancelButton.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }
    }
}