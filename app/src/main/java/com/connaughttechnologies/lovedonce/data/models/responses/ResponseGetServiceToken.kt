package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

data class ResponseGetServiceToken(
    val code: Int?, val message: String,
    val identity: String,
    val token: String,
    @SerializedName("admin_identity") val adminIdentity: String,
    @SerializedName("channel_unique_name") val channelUniqueName: String,
    @SerializedName("channel_friendly_name") val channelFriendlyName: String
)