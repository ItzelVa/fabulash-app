package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.itzel.fabulash.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBackButton.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        setUpRegister()
    }

    private fun setUpRegister() {
        binding.registerButton.setOnClickListener {
            //installSplashScreen()
            sendDataToServer()
        }
    }

    private fun sendDataToServer() {
        if(validateForm()){
            val dataStr = "Nombre:${binding.registerName.text.toString()}," +
                        "Apellido:${binding.registerLastname.text.toString()}," +
                        "Email:${binding.registerEmail.text.toString()}," +
                        "Telefono:${binding.registerPhone.text.toString()}," +
                        "Contrasena:${binding.registerPwd1.text.toString()},"

            Log.i("DATA SENT",dataStr)
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        with(binding){
            if(registerName.text.toString().isEmpty()){
                isValid = false
                registerName.error = getString(R.string.campo_requerido)
            }
            else{
                registerName.error= null
            }
            if(registerLastname.text.toString().isEmpty()){
                isValid = false
                registerLastname.error = getString(R.string.campo_requerido)
            }
            else{
                registerLastname.error = null
            }
            if(registerEmail.text.toString().isEmpty()){
                isValid = false
                registerEmail.error = getString(R.string.campo_requerido)
            }
            else{
                registerEmail.error = null
            }
            if(registerPhone.text.toString().isEmpty()){
                isValid = false
                registerPhone.error = getString(R.string.campo_requerido)
            }
            else{
                registerPhone.error = null
            }
            if(registerPwd1.text.toString().isEmpty()){
                isValid = false
                registerPwd1.error = getString(R.string.campo_requerido)
            }
            else{
                registerPwd1.error = null
            }
            if(registerPwd2.text.toString().isEmpty()){
                isValid = false
                registerPwd2.error = getString(R.string.campo_requerido)
            }
            else{
                registerPwd2.error = getString(R.string.campo_requerido)
            }
            if(registerPwd2.text.toString() != registerPwd1.text.toString()){
                isValid = false
                registerPwd2.error = "No coincide la contraseña"
            }
            else{
                registerPwd2.error = null
            }
        }
        return isValid
    }
}