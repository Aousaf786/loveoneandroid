package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName


data class RequestResetPasswordOtpVerified(@SerializedName("otp_code") val otpCode: String)