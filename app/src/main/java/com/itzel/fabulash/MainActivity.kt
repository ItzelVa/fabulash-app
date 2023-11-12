package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.itzel.fabulash.databinding.ActivityMainBinding
import com.itzel.fabulash.models.Usuario
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG: String = "OWN_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.startButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        getUsers()
    }

    private fun getUsers(){
        Api.request.getUsers().enqueue(object : Callback<MutableList<Usuario>> {
            override fun onResponse(
                call: Call<MutableList<Usuario>>,
                response: Response<MutableList<Usuario>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        for (user in it){
                            Log.i(TAG, "User name: ${user.nombre} ${user.apellido}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<Usuario>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error en la api", Toast.LENGTH_LONG)
            }
        })
    }
}