package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName
import java.sql.Struct

data class ResponseSingUp(
    val code: Int,
    val message: String,
    val data: SignUpData?,
//    val errors: String?
)

data class SignUpData(val user: User)
data class User(
    val name: String,
    val email: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val address: String,
    @SerializedName("api_token") var apiToken: String,
    @SerializedName("subscription_status") var subscriptionStatus: Int,
    @SerializedName("family_subscription_status") var familySubscriptionStatus: Int,
    @SerializedName("additional_info") var additionalInfo: String,
    @SerializedName("subscription_package") var subscriptionPackage: String?
)