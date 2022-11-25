package com.connaughttechnologies.lovedonce.data.models.responses

data class ResponseImageUploading(val code: Int, val message: String, val data: ImageUploadingData)
data class ImageUploadingData(val data: String)
