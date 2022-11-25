package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestAddMemorialProfile(
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("death_date") val deathDate: String,
    @SerializedName("cemetery_country") val cemeteryCountry: String,
    @SerializedName("cemetery_state") val cemeteryState: String,
    val images: MutableList<String>
)
