package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

data class ResponseGetAllServices(
    val code: Int,
    val message: String,
    val data: ServicesData?
)

data class ServicesData(val data: List<Service>)
data class Service(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    @SerializedName("payable_amount") var payableAmount: Double? = null,
    @SerializedName("cover_img") var coverImg: String? = null,
    var quantity: Int = 0
)