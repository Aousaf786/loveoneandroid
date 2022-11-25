package com.connaughttechnologies.lovedonce.data.models.responses

import com.google.gson.annotations.SerializedName

data class ResponseDiaryData(val code: Int, val message: String? = null, val data: DiaryData)
data class DiaryData(
    val id: Int,
    @SerializedName("daily_affirmation") val dailyAffirmation: String,
    @SerializedName("critical_tasks") val criticalTasks: CriticalTasks,
    val notes: String,
    @SerializedName("today_wins") val todayWins: String,
    @SerializedName("to_improve") val toImprove: String
)

data class CriticalTasks(
    @SerializedName("task_1") val task1: Int,
    @SerializedName("task_2") val task2: Int,
    @SerializedName("task_3") val task3: Int
)