package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

class ResponseGeoCode(val results: List<GeoCode>)
data class GeoCode(@SerializedName("formatted_address") val formattedAddress: String)