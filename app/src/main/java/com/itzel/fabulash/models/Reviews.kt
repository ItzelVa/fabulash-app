package com.itzel.fabulash.models

data class Reviews(
    val stars: Int,
    var user: Int,
    var date: String,
    var time: String,
    var category: String,
    var subject: String,
    var comment: String
)
