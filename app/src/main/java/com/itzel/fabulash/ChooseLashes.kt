package com.itzel.fabulash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itzel.fabulash.adapter.LashesAdapter
import com.itzel.fabulash.databinding.ActivityChooseLashesBinding
import com.itzel.fabulash.events.OnClickListener
import com.itzel.fabulash.models.Lashes

class ChooseLashes : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityChooseLashesBinding
    private var nombre : String = "No aplica"

    // Click al cardview escogido, obtiene los datos en dataStr, y cambia a la siguiente screen
    override fun onClick(lash: Lashes) {
        val dataStr = "Nombre:${lash.nombre}, Estilo:${lash.estilo}, Tamaño:${lash.tamano}"

        Log.i("DATA SENT LASHES",dataStr)

        val sharedPreferences = getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        nombre = lash.nombre
        editor.putString("chosenLashes", nombre)
        editor.apply()

        val intent=Intent(this,ChooseEmployee::class.java)
        startActivity(intent)
    }

    private lateinit var lashAdapter: LashesAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChooseLashesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lashAdapter = LashesAdapter(getLashes(),this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerLashes.apply {
            layoutManager = linearLayoutManager
            adapter = lashAdapter
        }

        // Variables para el filtrado, el R.array.filters obtiene un arreglo que hice por
        // de mientras, ese arreglo se va a llenar de acuerdo a la BD, en sus campos Estilo y Tamaño
        val filters = resources.getStringArray(R.array.filters)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_filter_lashes,filters)
        binding.filterBar.setAdapter(arrayAdapter)

    }

    override fun onStart() {
        super.onStart()

        // Botón hacia atrás
        binding.chooseLashesBackButton.setOnClickListener{
            val intent = Intent(this,ChooseService::class.java)
            startActivity(intent)
        }

        // Barra buscadora, filtra al cambio de cada letra
        binding.searchBar.addTextChangedListener {filter->
            val lashFilter = getLashes().filter {
                    lashes -> lashes.nombre.lowercase().contains(filter.toString().lowercase())  }
            lashAdapter.update(lashFilter)
        }

        // Filtro por dropdown item, filtra de acuerdo al texto seleccionado
        binding.filterBar.doAfterTextChanged {
                filter->
            val lashFilter = getLashes().filter {
                    lashes -> lashes.estilo.lowercase().contains(filter.toString().lowercase()) or
                    lashes.tamano.lowercase().contains(filter.toString().lowercase())}
            lashAdapter.update(lashFilter)
        }
    }

    private fun getLashes():MutableList<Lashes>{
        val lashes = mutableListOf<Lashes>()

        val l1 = Lashes("Nombre 1", "Natural", "Pequeño", "https://i.pinimg.com/736x/0e/73/ea/0e73ea2dfe1608599565848be2bdafc3.jpg")
        val l2 = Lashes("Nombre 2", "Exótico", "Grande", "https://i.pinimg.com/736x/0e/73/ea/0e73ea2dfe1608599565848be2bdafc3.jpg")
        val l3 = Lashes("Nombre 3", "Exótico", "Pequeño", "https://i.pinimg.com/736x/0e/73/ea/0e73ea2dfe1608599565848be2bdafc3.jpg")
        val l4 = Lashes("Nombre 4", "Natural", "Grande", "https://i.pinimg.com/736x/0e/73/ea/0e73ea2dfe1608599565848be2bdafc3.jpg")

        lashes.add(l1)
        lashes.add(l2)
        lashes.add(l3)
        lashes.add(l4)
        return lashes
    }
}