package com.itzel.fabulash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.itzel.fabulash.R
import com.itzel.fabulash.databinding.CardMyAppointmentsBinding
import com.itzel.fabulash.events.OnClickListenerMyAppointments
import com.itzel.fabulash.models.DataMyAppointments

class DataMyAppointmentsAdapter(private var appointments:MutableList<DataMyAppointments>, private val listener: OnClickListenerMyAppointments):
    RecyclerView.Adapter<DataMyAppointmentsAdapter.ViewHolder>() {

    private lateinit var context : Context
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = CardMyAppointmentsBinding.bind(view)

        fun setListener(myAppointments: DataMyAppointments,view:View,position: Int){
            binding.more.setOnClickListener {
                listener.onClick(myAppointments,view,position)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_my_appointments,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]
        with(holder){
            binding.statusText.text = appointment.estatus
            binding.fecha.text = appointment.fecha
            binding.hora.text = appointment.hora
            binding.nombrePersonal.text = appointment.empleado
            binding.servicio.text = appointment.servicio
            if(appointment.estatus.contains("Completada")){
                binding.statusAppointment.setCardBackgroundColor(ContextCompat.getColor(context,
                    R.color.yellow
                ))
                binding.more.isGone = true
            }
            if(appointment.estatus.contains("Cancelada")){
                binding.statusAppointment.setCardBackgroundColor(ContextCompat.getColor(context,
                    R.color.pink
                ))
                binding.more.isGone = true
            }
            Glide.with(context)
                .load(appointment.foto)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgPersonal)

            setListener(appointment,binding.more,position)
        }
    }

    override fun getItemCount(): Int = appointments.size


    fun remove(position: Int){
        var appointment = appointments[position]

        // Actualiza el texto en el objeto
        appointment.estatus = "Cancelada"

        notifyItemChanged(position)
    }
}