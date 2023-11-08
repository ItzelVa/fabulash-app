package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itzel.fabulash.databinding.ActivityChooseServiceBinding

class ChooseService : AppCompatActivity() {

    private lateinit var binding: ActivityChooseServiceBinding
    private var click : Int = 0
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
            if(click == (1 or 2)){
                val intent = Intent(this,ChooseLashes::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this,ChooseLashes::class.java)
                startActivity(intent)
            }
        }

        binding.aplicacionButton.setOnClickListener {
            binding.rizadoButton.setBackgroundColor(getResources().getColor(R.color.lightPink))
            binding.retiradoButton.setBackgroundColor(getResources().getColor(R.color.lightPurple))
            binding.aplicacionButton.setBackgroundColor(getResources().getColor(R.color.statusGreen))
            click = 1
        }

        binding.rizadoButton.setOnClickListener {
            binding.rizadoButton.setBackgroundColor(getResources().getColor(R.color.statusGreen))
            binding.retiradoButton.setBackgroundColor(getResources().getColor(R.color.lightPurple))
            binding.aplicacionButton.setBackgroundColor(getResources().getColor(R.color.yellow))
            click = 2
        }

        binding.retiradoButton.setOnClickListener {
            binding.rizadoButton.setBackgroundColor(getResources().getColor(R.color.lightPink))
            binding.retiradoButton.setBackgroundColor(getResources().getColor(R.color.statusGreen))
            binding.aplicacionButton.setBackgroundColor(getResources().getColor(R.color.yellow))
            click = 3
        }
    }
}