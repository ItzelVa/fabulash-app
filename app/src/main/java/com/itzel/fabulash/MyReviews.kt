package com.itzel.fabulash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.itzel.fabulash.network.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviews : AppCompatActivity(), OnClickListenerReviews{
    private lateinit var binding: ActivityMyReviewsBinding
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var reviewAdapter: MyReviewsAdapter
    private lateinit var recycler : RecyclerView
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_my_reviews)

        sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE)

        recycler = findViewById<RecyclerView>(R.id.recyclerReviews)

        val reviewsBack = findViewById<ImageButton>(R.id.myReviewsBackButton)
        reviewsBack.setOnClickListener {
            val intent = Intent(this,Account::class.java)
            startActivity(intent)
        }

        getReviews()
    }


    private fun getReviews() {
        val idUser = sharedPreferences.getInt("id_user", 0)
        var reviews = mutableListOf<Reviews>()

        updateRecyclerView(reviews)

        Api.request.getReviews(idUser).enqueue(object : Callback<MutableList<Reviews>>{
            override fun onResponse(
                call: Call<MutableList<Reviews>>,
                response: Response<MutableList<Reviews>>
            ) {
                if (response.isSuccessful){
                    reviews = response.body()!!
                    updateRecyclerView(reviews)
                } else {
                    Toast.makeText(this@MyReviews, "Error ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Reviews>>, t: Throwable) {
                Toast.makeText(this@MyReviews, "Error api", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onClick(review: Reviews, position: Int) {
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
    }

    private fun updateRecyclerView(reviews: MutableList<Reviews>){
        reviewAdapter = MyReviewsAdapter(reviews,this)
        linearLayoutManager = LinearLayoutManager(this)

        recycler.apply {
            layoutManager = linearLayoutManager
            adapter = reviewAdapter
        }
    }
}