package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseProfileDetail(
    val code: Int,
    val message: String,
    val data: SignInData?,
//    val errors: String?
)