package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.text.isDigitsOnly
import com.itzel.fabulash.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            val dataStr = "Email:${binding.loginEmail.text.toString()}," +
                    "Contrasena:${binding.loginPwd.text.toString()}"

            Log.i("DATA SENT",dataStr)
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
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