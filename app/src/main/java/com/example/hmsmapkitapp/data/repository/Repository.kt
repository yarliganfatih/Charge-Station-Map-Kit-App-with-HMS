package com.example.hmsmapkitapp.data.repository

import com.example.hmsmapkitapp.data.api.RetrofitInstance
import com.example.hmsmapkitapp.data.models.*
import retrofit2.Response

class Repository {
    suspend fun getChargeStationById(id: Int): Response<List<ChargeStation>> {
        return RetrofitInstance.openChargeMapStationService.getChargeStationById(id)
    }
    suspend fun getChargeStations(countrycode: String, distance: Int, latitude: Float, longitude: Float): Response<List<ChargeStation>> {
        return RetrofitInstance.openChargeMapStationService.getChargeStations(countrycode, distance, latitude, longitude)
    }
}