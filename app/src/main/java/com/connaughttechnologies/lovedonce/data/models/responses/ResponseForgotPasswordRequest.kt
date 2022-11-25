package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseForgotPasswordRequest(
    val code: Int,
    val message: String,
    val data: SignInData?
)