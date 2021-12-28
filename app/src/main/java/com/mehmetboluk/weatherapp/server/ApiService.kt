package com.mehmetboluk.weatherapp.server

import com.mehmetboluk.weatherapp.server.model.ResponseModel
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    //https://api.openweathermap.org/data/2.5/onecall?lat=37.422&lon=-122.0841&exclude=hourly,daily&units=metric&lang=us&appid=106dcca03e5b4845d9f55f209ed4e3d4

    @POST("/data/2.5/onecall?")
    suspend fun getWeatherApi(
        @Query("lat")
        lat : Double,
        @Query("lon")
        lon : Double,
        @Query("exclude")
        exclude : Array<String> = arrayOf("hourly,daily"),
        @Query("units")
        units : String = "metric",
        @Query("lang")
        lang : String = "us",
        @Query("appid")
        appid : String = "106dcca03e5b4845d9f55f209ed4e3d4"
    ) : ResponseModel
}