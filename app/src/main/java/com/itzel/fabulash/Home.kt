package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.itzel.fabulash.databinding.ActivityHomeBinding
import com.itzel.fabulash.models.NextAppointment
import com.itzel.fabulash.models.NextAppointmentData
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPreferences : SharedPreferences
    private var idClient = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        with(sharedPreferences){
            binding.homeUser.text = getString("name", "Invitad@")
            idClient = getInt("id_user", 0)
        }

        Api.request.getNextAppointment(idClient).enqueue(object : Callback<NextAppointment>{
            override fun onResponse(
                call: Call<NextAppointment>,
                response: Response<NextAppointment>
            ) {
                if (response.code() == 200){
                    binding.homeDay.text = response.body()!!.data.dia.toString()
                    binding.homeMonth.text = response.body()!!.data.mes
                    binding.homeHour.text = response.body()!!.data.hora
                    binding.homeStatus.text = response.body()!!.data.estatus
                    binding.homeStatus.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<NextAppointment>, t: Throwable) {
                Toast.makeText(this@Home, "Error en la api", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun openLink(view: View) {
        // Replace "https://example.com" with your desired URL
        val url = "https://maps.app.goo.gl/wrygBpxsgVsuJF4w5"

        // Create an Intent with the ACTION_VIEW action and the URL
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        // Start the browser activity
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        binding.homeAddAppointment.setOnClickListener {
            val intent = Intent(this, ChooseService::class.java)
            startActivity(intent)
        }

        binding.homePhoto.setOnClickListener {
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }

        binding.homeMyAppoint.setOnClickListener {
            val intent = Intent(this, MyAppointments::class.java)
            startActivity(intent)
        }

        binding.homeFeedback.setOnClickListener {
            val intent = Intent(this, NewReview::class.java)
            startActivity(intent)
            
        }

        binding.homeFilters.setOnClickListener {
            val intent = Intent(this, CameraPreview::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        if (!sharedPreferences.contains("id_user")){
            val intent = Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}