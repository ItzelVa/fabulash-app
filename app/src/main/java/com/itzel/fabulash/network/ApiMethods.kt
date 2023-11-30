package com.itzel.fabulash.network

import com.itzel.fabulash.NewReview
import com.itzel.fabulash.models.AppointmentPost
import com.itzel.fabulash.models.AppointmentUpdate
import com.itzel.fabulash.models.Cards
import com.itzel.fabulash.models.Employee
import com.itzel.fabulash.models.LashesResponse
import com.itzel.fabulash.models.LoginData
import com.itzel.fabulash.models.NextAppointment
import com.itzel.fabulash.models.Resena
import com.itzel.fabulash.models.Reviews
import com.itzel.fabulash.models.SessionData
import com.itzel.fabulash.models.SessionResponse
import com.itzel.fabulash.models.ViewAppointment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiMethods {
    @POST("api/login/")
    fun login(@Body newLogin: LoginData): Call<SessionResponse>

    @POST("api/usuario/")
    fun registerUser(@Body userData: SessionData): Call<Void>

    @PATCH("api/usuario/{id}/")
    fun updateUser(
        @Path("id") idClient: Int,
        @Body updatedUserInfo: SessionData): Call<Void>

    @GET("api/tarjeta/")
    fun getCardsInfo(@Query("id_cliente") idClient: Int): Call<MutableList<Cards>>

    @GET("api/tarjeta/")
    fun getCardInfo(
        @Query("id_cliente") idClient: Int,
        @Query("id_tarjeta") idCard: Int,
        ): Call<Cards>

    @POST("api/tarjeta/")
    fun createCard(
        @Body newCard: Cards
    ): Call<Void>

    @PUT("api/tarjeta/{id}/")
    fun updateCardInfo(
        @Path("id") id_tarjeta: Int,
        @Body newCardInfo: Cards
    ): Call<Void>

    @DELETE("api/tarjeta/{id}/")
    fun deleteCard(@Path("id") id_tarjeta: Int): Call<Void>

    @GET("api/empleado/")
    fun getEmployees(@Query("id_servicio") idService: Int): Call<MutableList<Employee>>

    @GET("api/empleado/")
    fun getEmployees(): Call<MutableList<Employee>>

    @GET("api/pestanas/")
    fun getLashes(): Call<LashesResponse>

    @POST("api/cita/")
    fun setAppointment(@Body newAppointment: AppointmentPost): Call<Void>

    @GET("api/cita/")
    fun getAppointments(
        @Query("id_cliente") idClient: Int
    ): Call<ViewAppointment>

    @GET("api/proxima-cita/")
    fun getNextAppointment(
        @Query("id_cliente") idClient: Int
    ): Call<NextAppointment>

    @PATCH("api/cita/{id}/")
    fun updateApointMent(
        @Path("id") idAppointment: Int,
        @Body updatedAppointment: AppointmentUpdate): Call<Void>

    @GET("api/resenas/")
    fun getReviews(
        @Query("id_user") idUser: Int
    ): Call<MutableList<Reviews>>

    @POST("api/resenas/")
    fun postReview(
        @Body newReview: Resena
    ): Call<Void>

    @DELETE("api/resenas/{id}/")
    fun deleteReview(
        @Path("id") idReview: Int
    ): Call<Void>
}