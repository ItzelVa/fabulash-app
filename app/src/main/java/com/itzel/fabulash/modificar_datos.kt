package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.itzel.fabulash.databinding.ActivityModificarDatosBinding
import com.itzel.fabulash.models.SessionData
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class modificar_datos : AppCompatActivity() {

    private lateinit var binding: ActivityModificarDatosBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        var idUser = 0

        with(sharedPreferences){
            idUser = getInt("id_user", 0)
            binding.registerName.setText(getString("name", ""))
            binding.registerLastname.setText(getString("lastname", ""))
            binding.registerEmail.setText(getString("email", ""))
            binding.registerPhone.setText(getString("phone", ""))
        }

        binding.dataBackButton.setOnClickListener {
            val view = Intent(this,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        binding.registerDataButton.setOnClickListener {
            val newUserInfo = SessionData(
                nombre = binding.registerName.text.toString(),
                apellido = binding.registerLastname.text.toString(),
                correo_electronico = binding.registerEmail.text.toString(),
                telefono = binding.registerPhone.text.toString(),
                contrasena = binding.newPasswordEditText1.text.toString()
            )

            Log.i("DATA", "ID: ${idUser}")

            Api.request.updateUser(idUser, newUserInfo).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        with(sharedPreferences.edit()){
                            putString("name", binding.registerName.text.toString())
                            putString("lastname", binding.registerLastname.text.toString())
                            putString("email", binding.registerEmail.text.toString())
                            putString("phone", binding.registerPhone.text.toString())
                            apply()
                        }
                        val view = Intent(this@modificar_datos,Account::class.java)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(view)
                        Toast.makeText(this@modificar_datos, "Informacion Guardada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@modificar_datos, "Error en la api", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

        binding.editPwd.setOnClickListener {

            val passwordChangeLayout =  binding.passwordChangeLayout
            if (passwordChangeLayout.visibility == View.GONE){
                passwordChangeLayout.visibility = View.VISIBLE
            }else{
                if(passwordChangeLayout.visibility == View.VISIBLE){
                    passwordChangeLayout.visibility = View.GONE
                }
            }
        }

        binding.layoutPwdMod.setOnClickListener {
            val  cambiarpwd = binding.layoutPwdMod
            if (cambiarpwd.visibility==View.GONE){
                cambiarpwd.visibility=View.VISIBLE
            }

        }

        val changePasswordTextView = binding.layoutPwdMod
        val passwordChangeLayout = binding.passwordChangeLayout

        changePasswordTextView.setOnClickListener {
            if (passwordChangeLayout.visibility == View.GONE) {
                passwordChangeLayout.visibility = View.VISIBLE
            }

        }
    }
}
