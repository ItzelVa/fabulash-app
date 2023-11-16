package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itzel.fabulash.databinding.ActivityChooseServiceBinding

class ChooseService : AppCompatActivity() {

    private lateinit var binding: ActivityChooseServiceBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var click : Int = 0
    private var chosen : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("service", Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()

        binding.chooseServiceBackButton.setOnClickListener{
            val intent = Intent(this,Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        binding.nextButton.setOnClickListener {

            Log.i("NEXT BUTTON CLICK",click.toString())
            Log.i("NEXT BUTTON SERVICE",chosen)

            if(click == 1){
                val intent = Intent(this,ChooseLashes::class.java)
                startActivity(intent)
            }
            if(click == 2){
                val intent = Intent(this,ChooseLashes::class.java)
                startActivity(intent)
            }
            if(click == 3){
                val intent = Intent(this,ChooseEmployee::class.java)
                startActivity(intent)
            }
        }

        binding.aplicacionButton.setOnClickListener {
            binding.rizadoButton.setBackgroundColor(getResources().getColor(R.color.lightPink))
            binding.retiradoButton.setBackgroundColor(getResources().getColor(R.color.lightPurple))
            binding.aplicacionButton.setBackgroundColor(getResources().getColor(R.color.statusGreen))
            click = 1
            chosen = "Aplicación"
            with(sharedPreferences.edit()){
                putInt("id_service", click)
                putString("name_service", chosen)
                putFloat("price_service", 250f)
                apply()
            }
        }

        binding.rizadoButton.setOnClickListener {
            binding.rizadoButton.setBackgroundColor(getResources().getColor(R.color.statusGreen))
            binding.retiradoButton.setBackgroundColor(getResources().getColor(R.color.lightPurple))
            binding.aplicacionButton.setBackgroundColor(getResources().getColor(R.color.yellow))
            click = 2
            chosen = "Rizado"
            with(sharedPreferences.edit()){
                putInt("id_service", click)
                putString("name_service", chosen)
                putFloat("price_service", 150f)
                apply()
            }
        }

        binding.retiradoButton.setOnClickListener {
            binding.rizadoButton.setBackgroundColor(getResources().getColor(R.color.lightPink))
            binding.retiradoButton.setBackgroundColor(getResources().getColor(R.color.statusGreen))
            binding.aplicacionButton.setBackgroundColor(getResources().getColor(R.color.yellow))
            click = 3
            chosen = "Retirado"
            with(sharedPreferences.edit()){
                putInt("id_service", click)
                putString("name_service", chosen)
                putFloat("price_service", 200f)
                apply()
            }
        }
    }
}