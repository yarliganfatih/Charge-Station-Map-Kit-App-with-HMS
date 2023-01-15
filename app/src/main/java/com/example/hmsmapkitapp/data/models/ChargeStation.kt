package com.example.hmsmapkitapp.data.models

data class ChargeStation(
    val ID: Int,
    val UsageTypeID: Int,
    val AddressInfo: AddressInfo,
    val Connections: Array<Connection>
)

data class AddressInfo(
    val ID: Int,
    val Title: String,
    val AddressLine1: String,
    val AddressLine2: String,
    val Town: String,
    val CountryID: Int,
    val Latitude: Float,
    val Longitude: Float,
    val ContactTelephone1 : String,
    val ContactEmail : String,
    val RelatedURL : String,
)

data class Connection(
    val ID: Int,
    val PowerKW: Double,
    val ConnectionType: ConnectionType
    val Quantity: Int,
)

data class ConnectionType(
    val FormalName: String
)