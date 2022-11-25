package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

data class ResponseOrderListing(
    val code: Int,
    val message: String,
    val data: OrderListingData?
)

data class OrderListingData(val data: List<OrderListing>)
data class OrderListingDataData(val records: List<OrderListing>)
data class OrderListing(
    val id: Int,
    @SerializedName("total_price") val totalPrice: Double,
    @SerializedName("ordered_date") val orderedDate: String,
    val description: String = "",
    val name: String
)