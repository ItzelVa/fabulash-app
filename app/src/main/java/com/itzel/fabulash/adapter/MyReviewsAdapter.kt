package com.itzel.fabulash.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itzel.fabulash.EditReview
import com.itzel.fabulash.R
import com.itzel.fabulash.RegisterCard
import com.itzel.fabulash.databinding.CardMyReviewsBinding
import com.itzel.fabulash.events.OnClickListenerReviews
import com.itzel.fabulash.models.Reviews

class MyReviewsAdapter(private val reviews: MutableList<Reviews>, private val listener: OnClickListenerReviews): RecyclerView.Adapter<MyReviewsAdapter.ViewHolder> (){
    private lateinit var context : Context
    private var pos: Int = 0
    inner class ViewHolder (view: View):RecyclerView.ViewHolder(view){
        val binding = CardMyReviewsBinding.bind(view)
        fun setListener(review: Reviews, position: Int){
            binding.root.setOnClickListener{
                listener.onClick(review, position)
            }
        }

        init {
            // Set an OnClickListener on the button
            binding.more.setOnClickListener { showPopupMenu(it) }
        }

        // ...


    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.review_popup, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.modificar -> {
                    // Handle menu item 1 click
                    val intent = Intent(context, EditReview::class.java)
                    context.startActivity(intent)
                    true
                }
                R.id.eliminar -> {
                    // Handle menu item 2 click
                    remove(pos)
                    true
                }
                // Add more cases for other menu items as needed
                else -> false
            }
        }

        popupMenu.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewsAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_my_reviews, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyReviewsAdapter.ViewHolder, position: Int) {
        val review = reviews[position]
        with(holder){
            setListener(review, position+1)
            binding.fecha.text = review.fecha
            binding.hora.text = ""
            binding.nombreCategoria.text = review.categoria
            binding.subject.text = review.destino
            binding.ratingBar.rating = review.estrellas?.toFloat()!!
        }

        holder.binding.more.setOnClickListener {
            pos= holder.adapterPosition
            showPopupMenu(it)
        }

        holder.binding.cv1.setOnClickListener {
            MaterialAlertDialogBuilder(context,R.style.AlertDialogTheme)
                .setTitle(R.string.comment)
                .setMessage(review.comentario)
                .setCancelable(true)
                .show()
        }


    }
    override fun getItemCount(): Int = reviews.size

    fun remove(position: Int){
        reviews.removeAt(position)
        notifyItemRemoved(position)
    }

}