package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapter.EmployeeAdapter
import com.itzel.fabulash.databinding.ActivityChooseEmployeeBinding
import com.itzel.fabulash.events.OnClickListenerEmployee
import com.itzel.fabulash.models.Employee

class ChooseEmployee : AppCompatActivity(), OnClickListenerEmployee {

    private lateinit var binding: ActivityChooseEmployeeBinding

    override fun onClick(employee: Employee) {
        val dataStr = "Nombre:${employee.nombre}, Experiencia:${employee.experiencia}, Estrellas:${employee.estrellas},URL:${employee.img}"
        Log.i("DATA SENT EMPLOYEE",dataStr)

        val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("chosenEmployee", employee.nombre)
        editor.apply()

        val intent=Intent(this, ChooseAppointment::class.java)
        startActivity(intent)
    }

    private lateinit var employeeAdapter : EmployeeAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeAdapter = EmployeeAdapter(getEmployees(),this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerEmployee.apply {
            layoutManager = linearLayoutManager
            adapter = employeeAdapter
        }

    }
    override fun onStart() {
        super.onStart()
        binding.chooseEmployeeBackButton.setOnClickListener{
            val intent = Intent(this,ChooseLashes::class.java)
            startActivity(intent)
        }
    }

    private fun getEmployees(): List<Employee> {
        val employees = mutableListOf<Employee>()

        val e1 = Employee("Juan Peréz","Rizado de pestañas",3,"https://img.favpng.com/4/1/19/computer-icons-user-profile-computer-software-png-favpng-7ujTL6FqkdsYJh37sSpqEZgZH.jpg")
        val e2 = Employee("Peter Porker","Aplicación de pestañas",5,"https://img.favpng.com/4/1/19/computer-icons-user-profile-computer-software-png-favpng-7ujTL6FqkdsYJh37sSpqEZgZH.jpg")

        employees.add(e1)
        employees.add(e2)
        employees.add(e1)
        employees.add(e2)

        return employees
    }
}
