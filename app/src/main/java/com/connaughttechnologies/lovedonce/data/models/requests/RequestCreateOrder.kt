package com.connaughttechnologies.lovedonce.data.models.requests

import com.connaughttechnologies.lovedonce.data.models.responses.Service
import com.google.gson.annotations.SerializedName

data class RequestCreateOrder(
    val services : List<Service>,
    @SerializedName("total_payable_amount") val totalPayableAmount: Double,
    @SerializedName("service_id") val serviceId: Int,
    val immediate: Int,
    @SerializedName("ordered_date") val orderedDate: String,
    @SerializedName("ordered_time") val orderedTime: String,
    val address: String,
    val lat: String,
    val lng: String,
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("card_exp_month") val cardExpMonth: Int,
    @SerializedName("card_exp_year") val cardExpYear: Int,
    @SerializedName("card_cvc") val cardCvv: String
)