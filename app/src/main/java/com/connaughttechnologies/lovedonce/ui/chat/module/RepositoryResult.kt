package com.connaughttechnologies.lovedonce.ui.chat.module

data class RepositoryResult<T>(
    val data: T,
    val requestStatus: RepositoryRequestStatus
)