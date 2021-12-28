package com.mehmetboluk.weatherapp.server

import android.app.AlertDialog
import android.media.RingtoneManager
import android.net.Uri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.mehmetboluk.weatherapp.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AppModule {

    fun getRetrofit(): ApiService =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    fun convertLongToDetailTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return format.format(date)
    }
    fun convertLongToShortTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }


}