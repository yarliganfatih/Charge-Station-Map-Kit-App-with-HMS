package com.example.hmsmapkitapp.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hmsmapkitapp.data.models.*
import com.example.hmsmapkitapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {
    val chargeStationById: MutableLiveData<Response<List<ChargeStation>>> = MutableLiveData()
    val chargeStations: MutableLiveData<Response<List<ChargeStation>>> = MutableLiveData()

    fun getChargeStationById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getChargeStationById(id)
            withContext(Dispatchers.Main) {
                chargeStationById.value = response
            }
        }
    }

    fun getChargeStations(countrycode: String, distance: Int, latitude: Float, longitude: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getChargeStations(countrycode, distance, latitude, longitude)
            withContext(Dispatchers.Main) {
                chargeStations.value = response
            }
        }
    }

}