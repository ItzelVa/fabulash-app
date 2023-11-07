package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itzel.fabulash.databinding.ActivityChooseLashesBinding

class ChooseLashes : AppCompatActivity() {

    private lateinit var binding : ActivityChooseLashesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseLashesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.chooseLashesBackButton.setOnClickListener{
            val intent = Intent(this,ChooseLashes::class.java)
            startActivity(intent)
        }
    }
}