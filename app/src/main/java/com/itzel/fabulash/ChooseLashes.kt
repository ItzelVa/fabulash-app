package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapter.LashesAdapter
import com.itzel.fabulash.databinding.ActivityChooseLashesBinding
import com.itzel.fabulash.events.OnClickListener
import com.itzel.fabulash.models.Lashes
import com.itzel.fabulash.models.LashesResponse
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseLashes : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityChooseLashesBinding
    private var nombre : String = "No aplica"
    private lateinit var lashAdapter: LashesAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var lashes: MutableList<Lashes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChooseLashesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("service", Context.MODE_PRIVATE)

        // Variables para el filtrado, el R.array.filters obtiene un arreglo que hice por
        // de mientras, ese arreglo se va a llenar de acuerdo a la BD, en sus campos Estilo y Tamaño
        val filters = resources.getStringArray(R.array.filters)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_filter_lashes,filters)
        binding.filterBar.setAdapter(arrayAdapter)

        getLashes()
    }

    override fun onStart() {
        super.onStart()

        // Botón hacia atrás
        binding.chooseLashesBackButton.setOnClickListener{
            val intent = Intent(this,ChooseService::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        // Barra buscadora, filtra al cambio de cada letra
        binding.searchBar.addTextChangedListener {filter->
            val lashFilter = lashes.filter {
                    lashes -> lashes.nombre.lowercase().contains(filter.toString().lowercase())  }
            lashAdapter.update(lashFilter)
        }

        // Filtro por dropdown item, filtra de acuerdo al texto seleccionado
        binding.filterBar.doAfterTextChanged {
                filter->
            val lashFilter = lashes.filter {
                    lashes -> lashes.tipo.lowercase().contains(filter.toString().lowercase()) or
                    lashes.tamano.lowercase().contains(filter.toString().lowercase())}
            lashAdapter.update(lashFilter)
        }
    }


    // Click al cardview escogido, obtiene los datos en dataStr, y cambia a la siguiente screen
    override fun onClick(lash: Lashes) {
        val dataStr = "Nombre:${lash.nombre}, Estilo:${lash.tipo}, Tamaño:${lash.tamano}"

        Log.i("DATA SENT LASHES",dataStr)

        val editor = sharedPreferences.edit()
        nombre = lash.nombre
        editor.putInt("id_lashes", lash.id)
        editor.putString("name_lashes", lash.nombre)
        editor.putFloat("price_lashes", lash.preciopes)

        editor.apply()

        val intent=Intent(this,ChooseEmployee::class.java)
        startActivity(intent)
    }

    private fun getLashes(){
        lashes = mutableListOf()
        updateReyclerView(lashes)

        Api.request.getLashes().enqueue(object : Callback<LashesResponse>{
            override fun onResponse(
                call: Call<LashesResponse>,
                response: Response<LashesResponse>
            ) {
                if (response.isSuccessful){
                    lashes = response.body()?.data!!
                    updateReyclerView(lashes)
                } else {
                    Toast.makeText(this@ChooseLashes, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LashesResponse>, t: Throwable) {
                Toast.makeText(this@ChooseLashes, "Error en la api", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateReyclerView(lashes: MutableList<Lashes>){
        lashAdapter = LashesAdapter(lashes,this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerLashes.apply {
            layoutManager = linearLayoutManager
            adapter = lashAdapter
        }
    }
}