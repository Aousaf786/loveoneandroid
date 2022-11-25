package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestSignUp(
    val name: String,
    val email: String,
    val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String,
    @SerializedName("fcm_token") val fcmToken: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val address: String
)