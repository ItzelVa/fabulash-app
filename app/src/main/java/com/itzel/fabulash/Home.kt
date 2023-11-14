package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itzel.fabulash.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.homeAddAppointment.setOnClickListener {
            val intent = Intent(this, ChooseService::class.java)
            startActivity(intent)
        }

        binding.homePhoto.setOnClickListener {
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }

        binding.homeMyAppoint.setOnClickListener {
            val intent = Intent(this, MyAppointments::class.java)
            startActivity(intent)
        }

        binding.homeFeedback.setOnClickListener {
            val intent = Intent(this, NewReview::class.java)
            startActivity(intent)
            
        }

    }
}