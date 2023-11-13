package com.itzel.fabulash.models

data class SessionData(
    val apellido: String,
    val contrasena: String,
    val correo_electronico: String,
    val hab: Boolean,
    val id: Int,
    val nombre: String,
    val telefono: String
)