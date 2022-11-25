package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseAllDiariesData(
    val code: Int,
    val message: String,
    val data: MyDiariesData
)

data class MyDiariesData(val data: List<DiaryData>)