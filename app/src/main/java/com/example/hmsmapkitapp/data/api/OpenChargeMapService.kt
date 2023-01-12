package com.example.hmsmapkitapp.data.api

import com.example.hmsmapkitapp.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface OpenChargeMapService {
    @GET("v3/poi/")
    suspend fun getChargeStationById(
        @Query("id") id: Int = 211219,
        @Query("key") key: String = "",
        //@Query("countrycode") countrycode: String = "TR",
        @Query("maxresults") maxresults: Int = 10,
        //@Query("polygon") polygon: String = "",
        @Query("output") output: String = "json",
        @Query("compact") compact: Boolean = true,
        @Query("verbose") verbose: Boolean = false,
    ): Response<List<ChargeStation>>
}