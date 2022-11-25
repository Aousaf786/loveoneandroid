package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestUpdatePaymentStatus(
    @SerializedName("order_id") val orderId: String,
    @SerializedName("product_id") val productId: String,
    @SerializedName("purchase_token") val purchaseToken: String,
    @SerializedName("purchase_expiry_date") val purchaseExpiryDate: String
)