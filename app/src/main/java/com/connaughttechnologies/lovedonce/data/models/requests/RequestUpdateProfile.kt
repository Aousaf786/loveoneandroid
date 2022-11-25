package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestUpdateProfile(
    val name: String,
    val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val address: String
)