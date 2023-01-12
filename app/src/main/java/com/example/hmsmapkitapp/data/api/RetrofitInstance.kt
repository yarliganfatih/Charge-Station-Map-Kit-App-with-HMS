package com.example.hmsmapkitapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openchargemap.io/v3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val openChargeMapStationService: OpenChargeMapService by lazy {
        retrofit.create(OpenChargeMapService::class.java)
    }
}