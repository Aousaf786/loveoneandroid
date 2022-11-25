package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseGetMemorialProfileResponse(
    val code: Int,
    val message: String,
    val data: MemorialProfile?
)
