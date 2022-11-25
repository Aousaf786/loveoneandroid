package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestSearchMemorialProfile(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("cemetery_country") val cemeteryCountry: String,
    @SerializedName("cemetery_state") val cemeteryState: String
)
