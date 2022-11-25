package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

data class ResponseGetMyMemorialProfiles(val code: Int, val message: String, val data: Data)
data class Data(val data: List<MemorialProfile>)
data class MemorialProfile(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("middle_name") val middleName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("death_date") val deathDate: String,
    @SerializedName("cemetery_country") val cemeteryCountry: String,
    @SerializedName("cemetery_state") val cemeteryState: String,
    @SerializedName("profile_image") val profileImage: String,
    val images: List<String>
)
