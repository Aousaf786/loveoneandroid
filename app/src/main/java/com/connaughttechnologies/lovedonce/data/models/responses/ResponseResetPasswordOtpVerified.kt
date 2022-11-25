package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseResetPasswordOtpVerified(
    val code: Int,
    val message: String,
    val data: OtpVerifiedData
)

data class OtpVerifiedData(val token: String)