package com.itzel.fabulash.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.itzel.fabulash.Cards
import com.itzel.fabulash.events.OnClickListenerDeleteCards
import com.itzel.fabulash.R
import com.itzel.fabulash.databinding.TarjetasCardsBinding

class DeleteCardsAdapter(private val cards: MutableList<Cards>, private val listener: OnClickListenerDeleteCards): RecyclerView.Adapter<DeleteCardsAdapter.ViewHolder> () {
    private lateinit var context : Context
    private var selectedItemPosition = RecyclerView.NO_POSITION
    private var onItemClickListener: OnClickListenerDeleteCards? = null

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

    fun setOnItemClickListener(listener: OnClickListenerDeleteCards) {
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

//    fun getSelectedPostion(): Int = selectedPosition
//    fun setChanges(position: Int){
//        notifyItemChanged(position)
//    }


    override fun getItemCount(): Int = cards.size
}