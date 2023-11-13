<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/ModifyCardsAdapter.kt
package com.itzel.fabulash.adapters
========
package com.itzel.fabulash.adapter
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/ModifyCardsAdapter.kt

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/ModifyCardsAdapter.kt
import com.itzel.fabulash.Cards
import com.itzel.fabulash.events.OnClickListenerModifyCards
========
import com.itzel.fabulash.models.Cards
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/ModifyCardsAdapter.kt
import com.itzel.fabulash.R
import com.itzel.fabulash.databinding.TarjetasCardsBinding
import com.itzel.fabulash.events.OnClickListenerModifyCards

class ModifyCardsAdapter (private val cards: MutableList<Cards>, private val listener: OnClickListenerModifyCards): RecyclerView.Adapter<ModifyCardsAdapter.ViewHolder> ()  {
    private lateinit var context : Context
    private var onItemClickListener: OnClickListenerModifyCards? = null

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val cardView: MaterialCardView = view.findViewById(R.id.mcv)
        val binding = TarjetasCardsBinding.bind(view)
        fun setListener(card: Cards, position: Int){
            binding.root.setOnClickListener{
                listener.onClick(card, position)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.tarjetas_cards, parent, false)
        return ViewHolder(view)
    }

    fun setOnItemClickListener(listener: OnClickListenerModifyCards) {
        this.onItemClickListener = listener
    }

    fun remove(position: Int){
        cards.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        with(holder){
            setListener(card, position+1)
            binding.cardType.text = card.getCardType(card.number)
            binding.cardName.text = card.name.toString()
            binding.cardDate.text = card.date.toString()
            binding.cardNumber.text = card.number.toString()
            binding.cardCvc.text = card.cvc.toString()

            if (selectedPosition == holder.adapterPosition) {
                // Apply styles for the selected card
                binding.mcv.strokeWidth = 15
                binding.mcv.strokeColor = ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.yellow
                )
            } else {
                // Apply styles for the unselected card
                binding.mcv.strokeWidth = 0 // No border
            }
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(card, position)
            selectedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }

    }
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun getItemCount(): Int = cards.size

}