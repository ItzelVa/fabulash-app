package com.itzel.fabulash.models

data class Cards(
    val apellido: String = "",
    val clvusu: Int = 0,
    val cvd: Int = 0,
    val fecha_vencimiento: String = "",
    val hab: Boolean? = true,
    val id: Int? = null,
    val nombre: String = "",
    val numero: String = ""
){
    fun getCardType(number: String):String {
        val cleanedNumber = number.replace(" ", "").replace("-", "") // Remove spaces and dashes
        val firstNum = cleanedNumber.take(2)
        return when {
            firstNum.startsWith("4") -> "Visa"
            firstNum.startsWith("5") -> "MasterCard"
            firstNum.startsWith("34") || firstNum.startsWith("37") -> "American Express"
            firstNum.startsWith("6") -> "Discover"
            else -> "Unknown"
        }
    }

}
