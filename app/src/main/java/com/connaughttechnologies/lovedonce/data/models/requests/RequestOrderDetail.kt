package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestOrderDetail(@SerializedName("order_id") val orderId: Int)