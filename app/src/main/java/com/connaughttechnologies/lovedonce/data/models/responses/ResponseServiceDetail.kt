package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseServiceDetail(
    val code: Int,
    val message: String,
    val data: Service?
)