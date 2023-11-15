package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itzel.fabulash.adapter.MyReviewsAdapter
import com.itzel.fabulash.databinding.ActivityMyReviewsBinding
import com.itzel.fabulash.databinding.CardMyReviewsBinding
import com.itzel.fabulash.events.OnClickListenerReviews
import com.itzel.fabulash.models.Reviews

class MyReviews : AppCompatActivity(), OnClickListenerReviews{
    private lateinit var binding: ActivityMyReviewsBinding
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var reviewAdapter: MyReviewsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_my_reviews)


        reviewAdapter = MyReviewsAdapter(getReviews(),this)
        linearLayoutManager = LinearLayoutManager(this)

        val reviewsBack = findViewById<ImageButton>(R.id.myReviewsBackButton)
        reviewsBack.setOnClickListener {
            val intent = Intent(this,Account::class.java)
            startActivity(intent)
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerReviews)
        recycler.apply {
            layoutManager = linearLayoutManager
            adapter = reviewAdapter
        }

    }


    private fun getReviews(): MutableList<Reviews> {
        val reviews = mutableListOf<Reviews>()
        val review1 = Reviews(5, 200, "17 de noviembre", "3:00 p.m.", "Servicio", "Aplicacion de extensiones de pestaña", "Me encantaron!")
        val review2 = Reviews(3, 200, "1 de noviembre", "4:00 p.m.", "Servicio", "Retirado de extensiones de pestaña", ":/")
        val review3 = Reviews(4, 200, "10 de octubre", "12:00 p.m.", "Servicio", "Rizado de pestañas", "Quedaron super bien!")
        val review4 = Reviews(4, 200, "13 de septiembre", "6:00 p.m.", "Empleado", "Carmen Perez Diaz", "Me conto mucho chisme. :)")
        val review5 = Reviews(5, 200, "22 de agosto", "4:00 p.m.", "Empleado", "Fulano Fulanito", "Tiene un gran humor.")

        reviews.add(review1)
        reviews.add(review2)
        reviews.add(review3)
        reviews.add(review4)
        reviews.add(review5)

        return reviews
    }

    override fun onClick(review: Reviews, position: Int) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
    }
}