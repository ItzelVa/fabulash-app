package com.itzel.fabulash.network

import com.itzel.fabulash.models.LoginData
import com.itzel.fabulash.models.SessionResponse
import com.itzel.fabulash.models.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiMethods {

    @GET("api/usuario/")
    fun getUsers(): Call<MutableList<UserData>>

    @POST("api/login/")
    fun login(@Body newLogin: LoginData): Call<SessionResponse>
}