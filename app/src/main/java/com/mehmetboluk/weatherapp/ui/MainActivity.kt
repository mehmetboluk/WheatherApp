package com.mehmetboluk.weatherapp.ui

import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mehmetboluk.weatherapp.databinding.ActivityMainBinding
import com.mehmetboluk.weatherapp.server.AppModule
import com.mehmetboluk.weatherapp.server.model.Coord
import com.mehmetboluk.weatherapp.viewModel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var coordinate = MutableLiveData<Coord>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onResume() {
        super.onResume()

        getLocation()
        coordinate.observe(this, Observer {
            viewModel.makeApiCall(it.latitude,it.longitude)
        })
        viewModel.apiResponse.observe(this, Observer {
            Log.d("response", it.toString())

            binding.apply {
                address.text = it.timezone
                updatedAt.text = AppModule.convertLongToDetailTime(System.currentTimeMillis() + it.current.dt)
                status.text = it.current.weather[0].main
                temp.text = "${it.current.temp.toString()}°C"
                sunrise.text = AppModule.convertLongToShortTime(System.currentTimeMillis() + it.current.sunrise)
                sunset.text = AppModule.convertLongToShortTime(System.currentTimeMillis() + it.current.sunset)
                wind.text = "${it.current.wind_speed}m/s"
                pressure.text = "${it.current.pressure}hPa"
                humidity.text = "% ${it.current.humidity}"
                llAlert.setOnClickListener { view ->
                    if(!it.alerts.isNullOrEmpty()){
                        showAlert(it.alerts[0].event,it.alerts[0].description, "OK", null)
                    }else{
                        Toast.makeText(this@MainActivity, "There is no alert message to show.", Toast.LENGTH_LONG).show()
                    }
                }

            }
        })


    }

    private fun getLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if(it != null){
                val coord = Coord(it.latitude,it.longitude)
                coordinate.postValue(coord)
            }
        }
    }

    fun showAlert(
        title: String,
        message: String,
        pButton: String,
        nButton: String?
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title).
                setMessage(message).
                setPositiveButton(pButton) { _, _->
                    onBackPressed()
                }

    }

}
