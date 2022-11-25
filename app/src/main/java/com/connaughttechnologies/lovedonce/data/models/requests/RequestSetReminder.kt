package com.connaughttechnologies.lovedonce.data.models.requests

import com.google.gson.annotations.SerializedName

data class RequestSetReminder(
    @SerializedName("schedule_date") val scheduleDate: String,
    @SerializedName("schedule_time") val scheduleTime: String,
    val title: String = "Reminder",
    val timezone: String,
    @SerializedName("fcm_token") val fcmToken: String
)