package com.itzel.fabulash.network

import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.models.LoginData
import com.itzel.fabulash.models.SessionResponse
import com.itzel.fabulash.models.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMethods {
    @POST("api/login/")
    fun login(@Body newLogin: LoginData): Call<SessionResponse>

    @GET("api/tarjeta/")
    fun getCardsInfo(@Query("id_cliente") idClient: Int): Call<MutableList<Cards>>

    @GET("api/tarjeta/")
    fun getCardInfo(
        @Query("id_cliente") idClient: Int,
        @Query("id_tarjeta") idCard: Int,
        ): Call<Cards>

    @PUT("api/tarjeta/{id}/")
    fun updateCardInfo(
        @Path("id") id_tarjeta: Int,
        @Body newCardInfo: Cards
    ): Call<Void>
}