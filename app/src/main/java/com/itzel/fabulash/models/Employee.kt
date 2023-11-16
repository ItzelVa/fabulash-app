package com.itzel.fabulash.models

data class Employee(
    val apellido: String,
    val clvpst: Int,
    val descripcion: String,
    val direccion: String,
    val estrellas: Int,
    val hab: Boolean,
    val id: Int,
    val imagen: String,
    val nombre: String,
    val telefono: String
)
