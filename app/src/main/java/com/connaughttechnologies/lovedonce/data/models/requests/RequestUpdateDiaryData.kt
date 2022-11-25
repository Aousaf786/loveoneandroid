package com.connaughttechnologies.lovedonce.data.models.requests

import com.connaughttechnologies.lovedonce.data.models.responses.CriticalTasks
import com.google.gson.annotations.SerializedName

class RequestUpdateDiaryData(
    val id:Int?,
    @SerializedName("daily_affirmation") val dailyAffirmation: String,
    @SerializedName("critical_tasks") val criticalTasks: CriticalTasks,
    val notes: String,
    @SerializedName("today_wins") val todayWins: String,
    @SerializedName("to_improve") val toImprove: String
)
