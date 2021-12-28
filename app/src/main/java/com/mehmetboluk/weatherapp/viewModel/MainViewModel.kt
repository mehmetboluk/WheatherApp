package com.mehmetboluk.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetboluk.weatherapp.server.AppModule
import com.mehmetboluk.weatherapp.server.model.ResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _apiResponse = MutableLiveData<ResponseModel>()
    val apiResponse : LiveData<ResponseModel>  get() = _apiResponse

    fun makeApiCall(lat : Double, lon : Double) = viewModelScope.launch(Dispatchers.IO) {
        _apiResponse.postValue(
            AppModule.getRetrofit().getWeatherApi(lat, lon)
        )
    }
}