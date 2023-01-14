package com.example.hmsmapkitapp.data.api

import com.example.hmsmapkitapp.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface OpenChargeMapService {
    @GET("v3/poi/")
    suspend fun getChargeStationById(
        @Query("id") id: Int = 211218,
        //@Query("countrycode") countrycode: String = "TR",
        @Query("maxresults") maxresults: Int = 10,
        //@Query("polygon") polygon: String = "",
        @Query("output") output: String = "json",
        @Query("compact") compact: Boolean = true,
        @Query("verbose") verbose: Boolean = false,
        @Query("key") key: String = "8",
    ): Response<List<ChargeStation>>

    @GET("v3/poi/")
    suspend fun getChargeStations(
        //@Query("id") id: Int = 211218,
        @Query("countrycode") countrycode: String = "TR",
        @Query("distance") distance: Int = 1,
        @Query("latitude") latitude: Float = 41.042165.toFloat(),
        @Query("longitude") longitude: Float = 29.0092591.toFloat(),
        @Query("maxresults") maxresults: Int = 10,
        //@Query("polygon") polygon: String = "",
        @Query("output") output: String = "json",
        @Query("compact") compact: Boolean = true,
        @Query("verbose") verbose: Boolean = false,
        @Query("key") key: String = "8",
    ): Response<List<ChargeStation>>
}