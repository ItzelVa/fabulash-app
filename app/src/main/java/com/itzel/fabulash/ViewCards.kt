package com.itzel.fabulash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itzel.fabulash.databinding.TarjetasBinding

class ViewCards : AppCompatActivity() {

    private lateinit var binding: TarjetasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TarjetasBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}