package com.itzel.fabulash.models

data class DataMyAppointments(
    val clvfp: Int,
    val clvpes: Int,
    val clvser: Int,
    val clvstat: Int,
    val clvusu: Int,
    var estatus: String,
    val empleado: String,
    val fecha: String,
    val foto: String,
    val hab: Boolean,
    val hora: String,
    val id: Int,
    val precio_final: Int,
    val servicio: String
) {
}
