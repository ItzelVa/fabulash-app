package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itzel.fabulash.databinding.ActivityConfirmedAppointmentBinding

class ConfirmedAppointment : AppCompatActivity() {

    private lateinit var binding : ActivityConfirmedAppointmentBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmedAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("service", Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()
        binding.finishButton.setOnClickListener {
            with(sharedPreferences.edit()){
                clear()
                apply()
            }

            val intent = Intent(this,Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}