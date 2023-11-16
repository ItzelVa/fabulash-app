package com.itzel.fabulash.models

data class AppointmentPost(
    val clvfp: Int? = 0,
    val clvpes: Int? = null,
    val clvser: Int? = 0,
    val clvstat: Int? = 1,
    val clvusu: Int? = 0,
    val fecha: String? = "",
    val hab: Boolean? = true,
    val hora: String = "",
    val precio_final: Float? = null
)