package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.itzel.fabulash.databinding.ActivityRegisterBinding
import com.itzel.fabulash.models.SessionData
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBackButton.setOnClickListener {
            val intent = Intent(this,Login::class.java)
            startActivity(intent)
            finish()
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
            val userData = SessionData(
                nombre = binding.registerName.text.toString(),
                apellido = binding.registerLastname.text.toString(),
                correo_electronico = binding.registerEmail.text.toString(),
                telefono = binding.registerPhone.text.toString(),
                contrasena = binding.registerPwd1.text.toString()
            )
            Api.request.registerUser(userData).enqueue(object : Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@Register, "Cuenta creada", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Register, MainActivity::class.java)
                        finishAffinity()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Register, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@Register, "Error en la api", Toast.LENGTH_SHORT).show()
                }

            })

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