package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    val email: String,
    val password: String,
    @SerializedName("fcm_token") val fcmToken: String,
)