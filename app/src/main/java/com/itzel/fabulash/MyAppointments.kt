package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itzel.fabulash.models.DataMyAppointments
import com.itzel.fabulash.adapter.DataMyAppointmentsAdapter
import com.itzel.fabulash.databinding.ActivityMyAppointmentsBinding
import com.itzel.fabulash.databinding.CardMyAppointmentsBinding
import com.itzel.fabulash.events.OnClickListenerMyAppointments
import com.itzel.fabulash.models.AppointmentUpdate
import com.itzel.fabulash.models.ViewAppointment
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppointments : AppCompatActivity(), OnClickListenerMyAppointments {

    private lateinit var binding: ActivityMyAppointmentsBinding
    private lateinit var appointmentAdapter : DataMyAppointmentsAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        getAppointments()
    }

    override fun onStart() {
        super.onStart()
        binding.myAppointmentsBackButton.setOnClickListener {
            val intent = Intent(this,Home::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

     override fun onClick(myAppointments: DataMyAppointments, view:View,position: Int) {
        if(!(myAppointments.estatus.contains("Completada")) and !(myAppointments.estatus.contains("Cancelada"))){
            showPopUpMenu(view,myAppointments,position)
        }
    }

    private fun showPopUpMenu(view:View, myAppointments: DataMyAppointments,position: Int) {
        val popUpMenu = PopupMenu(view.context,view)


        popUpMenu.inflate(R.menu.popup_menu_my_appointments)
        popUpMenu.show()
        popUpMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.eliminar ->{

                    MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
                        .setTitle("Estás a punto de cancelar tu cita")
                        .setMessage(myAppointments.fecha + " - " + myAppointments.hora)
                        .setNeutralButton(R.string.cancelar, {dialog, i -> })
                        .setPositiveButton("Cancelar cita", {dialog, i ->
                            val updatedAppointment = AppointmentUpdate(
                                clvstat = 3
                            )
                            Api.request.updateApointMent(myAppointments.id, updatedAppointment).enqueue(object : Callback<Void>{
                                override fun onResponse(
                                    call: Call<Void>,
                                    response: Response<Void>
                                ) {
                                    appointmentAdapter.remove(position)
                                    Toast.makeText(this@MyAppointments, "Cita cancelada", Toast.LENGTH_SHORT).show()
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }

                            })
                        })
                        .setCancelable(true)
                        .show()
                    true
                }
                R.id.modificar -> {

                    val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("currentDate", myAppointments.fecha + " - " + myAppointments.hora)
                    editor.putInt("appointmentId", myAppointments.id)
                    editor.apply()

                    val intent = Intent(this,Reprogramar::class.java)
                    startActivity(intent)
                    true
                }
                else -> true
            }
        }
    }

    private fun getAppointments(){
        val idClient = sharedPreferences.getInt("id_user", 0)
        var appointments = mutableListOf<DataMyAppointments>()
        updateRecyclerView(appointments)

        Api.request.getAppointments(idClient).enqueue(object : Callback<ViewAppointment>{
            override fun onResponse(
                call: Call<ViewAppointment>,
                response: Response<ViewAppointment>
            ) {
                if (response.isSuccessful){
                    appointments = response.body()?.data!!
                    updateRecyclerView(appointments)
                }
            }

            override fun onFailure(call: Call<ViewAppointment>, t: Throwable) {
                Toast.makeText(this@MyAppointments, "Error en la api", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateRecyclerView(appointments: MutableList<DataMyAppointments>){
        appointmentAdapter = DataMyAppointmentsAdapter(appointments,this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerAppointment.apply {
            layoutManager = linearLayoutManager
            adapter = appointmentAdapter
        }
    }
}