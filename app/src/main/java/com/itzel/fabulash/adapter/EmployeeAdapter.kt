<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/EmployeeAdapter.kt
package com.itzel.fabulash.adapters
========
package com.itzel.fabulash.adapter
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/EmployeeAdapter.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/EmployeeAdapter.kt
import com.itzel.fabulash.Employee
import com.itzel.fabulash.events.OnClickListenerEmployee
========
import com.itzel.fabulash.models.Employee
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/EmployeeAdapter.kt
import com.itzel.fabulash.R
import com.itzel.fabulash.databinding.CardEmployeeBinding
import com.itzel.fabulash.events.OnClickListenerEmployee

class EmployeeAdapter(private var employees: List<Employee>, private val listener: OnClickListenerEmployee):RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    private lateinit var context: Context
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = CardEmployeeBinding.bind(view)

        fun setListener(employee: Employee){
            binding.root.setOnClickListener {
                listener.onClick(employee)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_employee,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employees[position]
        with(holder){
            setListener(employee)
            binding.nameEmployee.text = employee.nombre
            binding.expEmployee.text = employee.experiencia
            if(employee.estrellas==0){
                binding.starsEmployee.text = ""
            }
            if (employee.estrellas==1){
                binding.starsEmployee.text = "⭐"
            }
            if (employee.estrellas==2){
                binding.starsEmployee.text = "⭐⭐"
            }
            if (employee.estrellas==3){
                binding.starsEmployee.text = "⭐⭐⭐"
            }
            if (employee.estrellas==4){
                binding.starsEmployee.text = "⭐⭐⭐⭐"
            }
            if (employee.estrellas==5){
                binding.starsEmployee.text = "⭐⭐⭐⭐⭐"
            }
            Glide.with(context)
                .load(employee.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .circleCrop()
                .into(binding.imgEmployee)
        }
    }

    override fun getItemCount(): Int = employees.size
}