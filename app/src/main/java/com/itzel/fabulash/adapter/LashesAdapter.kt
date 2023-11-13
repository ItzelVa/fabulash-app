<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/LashesAdapter.kt
package com.itzel.fabulash.adapters
========
package com.itzel.fabulash.adapter
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/LashesAdapter.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/LashesAdapter.kt
import com.itzel.fabulash.Lashes
import com.itzel.fabulash.events.OnClickListener
========
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/LashesAdapter.kt
import com.itzel.fabulash.R
import com.itzel.fabulash.databinding.CardLashesBinding
import com.itzel.fabulash.events.OnClickListener
import com.itzel.fabulash.models.Lashes

class LashesAdapter(private var lashes: List<Lashes>, private val listener: OnClickListener): RecyclerView.Adapter<LashesAdapter.ViewHolder>() {

    private lateinit var context : Context
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = CardLashesBinding.bind(view)

        fun setListener(lash: Lashes){
            binding.root.setOnClickListener {
                listener.onClick(lash)
            }
        }
    }

    fun update(lashes: List<Lashes>){
        this.lashes = lashes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_lashes,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lash = lashes[position]
        with(holder){
            setListener(lash)
            binding.nameLash.text = lash.nombre
            binding.styleLash.text = lash.estilo
            binding.sizeLash.text = lash.tamano
            Glide.with(context)
                .load(lash.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imgLash)
        }
    }

    override fun getItemCount(): Int = lashes.size
}