package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.itzel.fabulash.databinding.ActivityMainBinding
import com.itzel.fabulash.models.UserData
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences : SharedPreferences
    private val TAG: String = "OWN_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        if (sharedPreferences.contains("id_user")){
            finish()
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }

        binding.startButton.setOnClickListener {
            finish()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        getUsers()
    }

    private fun getUsers(){
        Api.request.getUsers().enqueue(object : Callback<MutableList<UserData>> {
            override fun onResponse(
                call: Call<MutableList<UserData>>,
                response: Response<MutableList<UserData>>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        for (user in it){
                            Log.i(TAG, "User name: ${user.nombre} ${user.apellido}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<UserData>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error en la api", Toast.LENGTH_LONG)
            }
        })
    }
}