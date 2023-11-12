package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.text.isDigitsOnly
import com.itzel.fabulash.databinding.ActivityLoginBinding
import com.itzel.fabulash.models.LoginData
import com.itzel.fabulash.models.SesionResponse
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        binding.loginRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        setUpButton()
    }

    private fun setUpButton() {
        binding.loginButton.setOnClickListener {
            //installSplashScreen()
            sendDataToServer()
        }
    }

    private fun sendDataToServer() {

        if (validateForm()){
            val login = LoginData(
                binding.loginPwd.text.toString(),
                binding.loginEmail.text.toString()
            )

            Api.request.login(login).enqueue(object : Callback<SesionResponse>{
                override fun onResponse(
                    call: Call<SesionResponse>,
                    response: Response<SesionResponse>
                ) {
                    if(response.isSuccessful){
                        // Save info into prefereces
                        with(sharedPreferences.edit()){
                            putInt("id_user", response.body()?.data?.id!!)
                            putString("name", response.body()?.data?.nombre)
                            apply()
                        }

                        finish()
                        val intent = Intent(this@Login, Welcome::class.java)
                        startActivity(intent)
                    } else {
                        binding.loginEmail.error = "Correo y/o contraseña incorrectos"
                    }
                }

                override fun onFailure(call: Call<SesionResponse>, t: Throwable) {
                    Toast.makeText(
                        this@Login,
                        "Ocurrió un error con la api",
                        Toast.LENGTH_LONG).show()
                }
            })

            //Redirect to next activity


//            val dataStr = "Email:${binding.loginEmail.text.toString()}," +
//                    "Contrasena:${binding.loginPwd.text.toString()}"
//
//            Log.i("DATA SENT",dataStr)
//            val intent = Intent(this, Welcome::class.java)
//            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        with(binding){
            if(loginEmail.text.toString().isEmpty()){
                isValid = false
                loginEmail.error = getString(R.string.campo_requerido)
            }
            else{
                loginEmail.error= null
            }
            if(loginPwd.text.toString().isEmpty()){
                isValid = false
                loginPwd.error = getString(R.string.campo_requerido)
            }
            else{
                loginPwd.error= null
            }
        }
        return isValid
    }
}