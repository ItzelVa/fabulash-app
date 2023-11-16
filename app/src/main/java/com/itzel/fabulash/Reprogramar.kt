package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.itzel.fabulash.databinding.ActivityReprogramarBinding
import com.itzel.fabulash.models.AppointmentUpdate
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reprogramar : AppCompatActivity() {

    private lateinit var binding: ActivityReprogramarBinding
    private lateinit var sharedPreferences: SharedPreferences
    // Bandera para avanzar dependiendo si escogió un día valido (No ocupado)
    private var ban = false
    private var dateComplete: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReprogramarBinding.inflate(layoutInflater)

        sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val date = sharedPreferences.getString("currentDate", "")

        binding.fechaHora.text = date

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.chooseAppointmentBackButton.setOnClickListener {
            val intent = Intent(this,MyAppointments::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        // Listener del botón siguiente
        binding.nextButton.setOnClickListener {
            if(ban){
                if(!binding.hourBar.text.isEmpty() and !(binding.hourBar.text.contains("Hora"))){
                    val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("chosenDate", dateComplete)
                    editor.putString("chosenHour", binding.hourBar.text.toString())
                    editor.apply()
                    val appointmentID = sharedPreferences.getInt("appointmentId", 0)
                    val updatedAppointment = AppointmentUpdate(
                        fecha = dateComplete,
                        hora = binding.hourBar.text.toString()
                    )

                    Api.request.updateApointMent(appointmentID, updatedAppointment).enqueue(object : Callback<Void>{
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.isSuccessful){
                                val intent = Intent(this@Reprogramar,MyAppointments::class.java)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(this@Reprogramar, "Error en la api", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }

        val daysNo = intArrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27)
        val daysMaybe = intArrayOf(28)
        val daysYes = intArrayOf(29,30)

        val hourNo = arrayOf("")
        // Considerando un horario de trabajo de 9am a 6pm, con poca disponibilidad unas 3 u 4 hrs
        val hourMaybe = arrayOf("10:00","12:00","16:00")
        val hourYes = arrayOf("9:00","10:00","11:00","12:00","13:00","14:00","16:00","17:00","18:00")

        // Llenado de dropdown items
        val arrayAdapterNo = ArrayAdapter(this,R.layout.dropdown_filter_lashes,hourNo)
        val arrayAdapterMaybe = ArrayAdapter(this,R.layout.dropdown_filter_lashes,hourMaybe)
        val arrayAdapterYes = ArrayAdapter(this,R.layout.dropdown_filter_lashes,hourYes)

        var year = binding.calendar.year
        var month = binding.calendar.month
        var day = binding.calendar.dayOfMonth

        binding.calendar.init(year, month, day, object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                // Obtiene el día seleccionado
                var selectedDay = view.dayOfMonth

                binding.hourBar.setText("Hora")

                if(daysNo.contains(selectedDay)){
                    binding.colorDisp.setCardBackgroundColor(getResources().getColor(R.color.yellow))
                    // Pintado de opciones dropdowm
                    binding.hourBar.setAdapter(arrayAdapterNo)
                    ban = false
                }

                if(daysMaybe.contains(selectedDay)){
                    binding.colorDisp.setCardBackgroundColor(getResources().getColor(R.color.lightOrange))
                    // Pintado de opciones dropdown
                    binding.hourBar.setAdapter(arrayAdapterMaybe)
                    ban = true
                }

                if(daysYes.contains(selectedDay)){
                    binding.colorDisp.setCardBackgroundColor(getResources().getColor(R.color.statusGreen))
                    binding.hourBar.setAdapter(arrayAdapterYes)
                    ban = true
                }

                // variable que guarda la fecha DIA / MES / AÑO
                dateComplete = year.toString() +"-"+ month.toString() +"-"+ selectedDay.toString()
                Log.i("DATE",dateComplete)
            }
        })

    }

}