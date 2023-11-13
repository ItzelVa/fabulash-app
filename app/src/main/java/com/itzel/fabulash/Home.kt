package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itzel.fabulash.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        with(sharedPreferences){
            binding.homeUser.text = getString("name", "Invitad@")
        }

        //Set username
    }

    override fun onStart() {
        super.onStart()
        binding.homeAddAppointment.setOnClickListener {
            val intent = Intent(this,ChooseService::class.java)
            startActivity(intent)
        }
        binding.homePhoto.setOnClickListener{
            val intent = Intent(this,Account::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!sharedPreferences.contains("id_user")){
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}