package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itzel.fabulash.databinding.ActivityChooseServiceBinding

class ChooseService : AppCompatActivity() {

    private lateinit var binding: ActivityChooseServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChooseServiceBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.chooseServiceBackButton.setOnClickListener{
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }

        binding.nextButton.setOnClickListener {
            val intent = Intent(this,ChooseLashes::class.java)
            startActivity(intent)
        }
    }
}