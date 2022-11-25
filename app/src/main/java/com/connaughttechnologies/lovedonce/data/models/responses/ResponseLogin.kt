package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseLogin(
    val code: Int,
    val message: String,
    val data: SignInData?,
//    val errors: String?
)

data class SignInData(val user: User)