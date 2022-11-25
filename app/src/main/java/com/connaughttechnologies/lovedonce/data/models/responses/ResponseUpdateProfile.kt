package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseUpdateProfile(
    val code: Int,
    val message: String,
    val data: UpdateProfileData?,
//    val errors: String?
)

data class UpdateProfileData(val user: User)