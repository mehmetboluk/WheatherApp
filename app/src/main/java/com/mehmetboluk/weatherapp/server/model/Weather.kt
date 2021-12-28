package com.mehmetboluk.weatherapp.server.model

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)