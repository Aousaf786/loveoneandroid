package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

data class ResponseOrderDetail(
    val code: Int,
    val message: String,
    val data: OrderDetail
)

data class OrderDetail(
    val id: Int,
    @SerializedName("total_price") val totalPrice: Double,
    val immediate: Int,
    @SerializedName("ordered_date") val orderDate: String,
    val status: Int,
    val address: String,
    @SerializedName("ordered_time") val orderTime: String,
    @SerializedName("status_string") val statusString: String
)