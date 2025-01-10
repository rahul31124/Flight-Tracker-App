package com.example.airvibes

data class webcategorydc(
    val idw: Int,
    val wname: String,
    val wimageUrl: String,
    var price: Double = 0.0 // Mutable price field
)
