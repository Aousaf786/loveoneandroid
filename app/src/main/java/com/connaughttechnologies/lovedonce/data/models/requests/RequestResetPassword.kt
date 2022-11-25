package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestResetPassword(
    val token: String, val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String
)