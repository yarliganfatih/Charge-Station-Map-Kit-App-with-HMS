package com.example.hmsmapkitapp.data.models

data class ChargeStation(
    val ID: Int,
    val UsageTypeID: Int,
    val AddressInfo: AddressInfo
)

data class AddressInfo(
    val ID: Int,
    val Title: String,
    val AddressLine1: String,
    val AddressLine2: String,
    val Town: String,
    val CountryID: Int,
    val Latitude: Float,
    val Longitude: Float
)
