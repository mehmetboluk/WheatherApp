package com.mehmetboluk.weatherapp.server.model

data class ResponseModel(
    val alerts: List<Alert>,
    val current: Current,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)