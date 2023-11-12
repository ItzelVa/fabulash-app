package com.itzel.fabulash.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private val BASE_URL: String = "http://192.168.1.67:8000/"

    val request = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiMethods::class.java)
}