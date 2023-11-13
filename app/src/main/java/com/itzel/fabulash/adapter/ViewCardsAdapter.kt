<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/ViewCardsAdapter.kt
package com.itzel.fabulash.adapters
========
package com.itzel.fabulash.adapter
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/ViewCardsAdapter.kt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
<<<<<<<< HEAD:app/src/main/java/com/itzel/fabulash/adapters/ViewCardsAdapter.kt
import com.itzel.fabulash.Cards
import com.itzel.fabulash.events.OnClickListenerCards
========
import com.itzel.fabulash.models.Cards
>>>>>>>> 838de2023057bb11bf853efeaf96532c070ed988:app/src/main/java/com/itzel/fabulash/adapter/ViewCardsAdapter.kt
import com.itzel.fabulash.R
import com.itzel.fabulash.databinding.TarjetasCardsBinding
import com.itzel.fabulash.events.OnClickListenerCards

class ViewCardsAdapter(private val cards: MutableList<Cards>, private val listener: OnClickListenerCards): RecyclerView.Adapter<ViewCardsAdapter.ViewHolder> () {
    private lateinit var context : Context
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        with(holder){
            setListener(card, position+1)
            binding.cardType.text = card.getCardType(card.number)
            binding.cardName.text = card.name.toString()
            binding.cardDate.text = card.date.toString()
            binding.cardNumber.text = card.number.toString()
            binding.cardCvc.text = card.cvc.toString()
        }
    }

    override fun getItemCount(): Int = cards.size
    fun remove(position: Int){
        cards.removeAt(position)
        notifyItemRemoved(position)
    }

}