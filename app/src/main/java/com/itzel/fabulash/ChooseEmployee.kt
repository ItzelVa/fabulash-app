package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapter.EmployeeAdapter
import com.itzel.fabulash.databinding.ActivityChooseEmployeeBinding
import com.itzel.fabulash.events.OnClickListenerEmployee
import com.itzel.fabulash.models.Employee
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseEmployee : AppCompatActivity(), OnClickListenerEmployee {

    private lateinit var binding: ActivityChooseEmployeeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var employeeAdapter : EmployeeAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("service", Context.MODE_PRIVATE)

        getEmployees()
    }
    override fun onStart() {
        super.onStart()
        binding.chooseEmployeeBackButton.setOnClickListener{
            val intent = Intent(this,ChooseLashes::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onClick(employee: Employee) {
        //val dataStr = "Nombre:${employee.nombre}, Experiencia:${employee.descripcion}, Estrellas:${employee.estrellas},URL:${employee.img}"
        //Log.i("DATA SENT EMPLOYEE",dataStr)


        val editor = sharedPreferences.edit()
        editor.putInt("id_employee", employee.id)
        editor.apply()

        val intent=Intent(this, ChooseAppointment::class.java)
        startActivity(intent)
    }



    private fun getEmployees() {
        var employees = mutableListOf<Employee>()
        val idService = sharedPreferences.getInt("id_service", 0)
        updateRecyclerView(employees)

        Api.request.getEmployees(idService).enqueue(object : Callback<MutableList<Employee>>{
            override fun onResponse(
                call: Call<MutableList<Employee>>,
                response: Response<MutableList<Employee>>
            ) {
                if (response.isSuccessful){
                    employees = response.body()!!
                    updateRecyclerView(employees)
                } else {
                    Toast.makeText(this@ChooseEmployee, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Employee>>, t: Throwable) {
                Toast.makeText(this@ChooseEmployee, "Fallo en la api", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun updateRecyclerView(employees: MutableList<Employee>){
        employeeAdapter = EmployeeAdapter(employees,this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerEmployee.apply {
            layoutManager = linearLayoutManager
            adapter = employeeAdapter
        }
    }
}
