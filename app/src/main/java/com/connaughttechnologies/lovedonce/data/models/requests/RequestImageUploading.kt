package com.connaughttechnologies.lovedonce.data.models.requests

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class RequestImageUploading(
    val type: RequestBody,
    val image: MultipartBody.Part
)
