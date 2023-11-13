package com.itzel.fabulash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapter.DataMyAppointments
import com.itzel.fabulash.adapter.DataMyAppointmentsAdapter
import com.itzel.fabulash.databinding.ActivityMyAppointmentsBinding
import com.itzel.fabulash.events.OnClickListenerMyAppointments

class MyAppointments : AppCompatActivity(), OnClickListenerMyAppointments {

    private lateinit var binding: ActivityMyAppointmentsBinding
    private lateinit var appointmentAdapter : DataMyAppointmentsAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onClick(myAppointments: DataMyAppointments) {
        if(!(myAppointments.status.contains("Completada")) and !(myAppointments.status.contains("Cancelada"))){
            Toast.makeText(this,myAppointments.status,Toast.LENGTH_SHORT).show()
            registerForContextMenu(binding.recyclerAppointment)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.popup_menu_my_appointments,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.modificar -> Toast.makeText(this,"Reprogramar",Toast.LENGTH_SHORT).show()
            R.id.eliminar -> Toast.makeText(this,"Cancelar",Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
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

    private fun getAppointments(): List<DataMyAppointments> {
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