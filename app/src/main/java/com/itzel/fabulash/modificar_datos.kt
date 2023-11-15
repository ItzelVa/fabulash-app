package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.itzel.fabulash.databinding.ActivityModificarDatosBinding
import com.itzel.fabulash.databinding.ActivityRegisterBinding

class modificar_datos : AppCompatActivity() {

    private lateinit var binding: ActivityModificarDatosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dataBackButton.setOnClickListener {
            val view = Intent(this,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            finish()
        }

        binding.registerDataButton.setOnClickListener {
            val view = Intent(this,Account::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(view)
            Toast.makeText(this, "Informacion Guardada", Toast.LENGTH_SHORT).show()
            finish()
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
