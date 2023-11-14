package com.itzel.fabulash

import android.content.Context
import android.content.Intent
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

class MyAppointments : AppCompatActivity(), OnClickListenerMyAppointments {

    private lateinit var binding: ActivityMyAppointmentsBinding
    private lateinit var appointmentAdapter : DataMyAppointmentsAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

     override fun onClick(myAppointments: DataMyAppointments, view:View,position: Int) {
        if(!(myAppointments.status.contains("Completada")) and !(myAppointments.status.contains("Cancelada"))){
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
                            appointmentAdapter.remove(position)
                            Toast.makeText(this, "Cita cancelada", Toast.LENGTH_SHORT).show()
                        })
                        .setCancelable(true)
                        .show()
                    true
                }
                R.id.modificar -> {

                    val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("currentDate", myAppointments.fecha + " - " + myAppointments.hora)
                    editor.apply()

                    val intent = Intent(this,Reprogramar::class.java)
                    startActivity(intent)
                    true
                }
                else -> true
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAppointmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appointmentAdapter = DataMyAppointmentsAdapter(getAppointments(),this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerAppointment.apply {
            layoutManager = linearLayoutManager
            adapter = appointmentAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        binding.myAppointmentsBackButton.setOnClickListener {
            val intent = Intent(this,Home::class.java)
            startActivity(intent)
        }
    }

    private fun getAppointments(): MutableList<DataMyAppointments> {
        val appointments = mutableListOf<DataMyAppointments>()

        val a1 = DataMyAppointments("Cancelada","17 de Septiembre","02:00 p.m.","Carmen Pérez Díaz","Rizado de pestañas","https://img.favpng.com/4/1/19/computer-icons-user-profile-computer-software-png-favpng-7ujTL6FqkdsYJh37sSpqEZgZH.jpg")
        val a2 = DataMyAppointments("Completada","17 de Octubre","02:00 p.m.","Carmen Pérez Díaz","Retirado de extensiones de pestañas","https://img.favpng.com/4/1/19/computer-icons-user-profile-computer-software-png-favpng-7ujTL6FqkdsYJh37sSpqEZgZH.jpg")
        val a3 = DataMyAppointments("En proceso","17 de Noviembre","02:00 p.m.","Carmen Pérez Díaz","Aplicación de pestañas","https://img.favpng.com/4/1/19/computer-icons-user-profile-computer-software-png-favpng-7ujTL6FqkdsYJh37sSpqEZgZH.jpg")

        appointments.add(a3)
        appointments.add(a2)
        appointments.add(a1)

        return appointments
    }
}