package com.connaughttechnologies.lovedonce.ui.chat.module

sealed class RepositoryRequestStatus {
    object FETCHING : RepositoryRequestStatus()
    object SUBSCRIBING : RepositoryRequestStatus()
    object COMPLETE : RepositoryRequestStatus()
    class Error(val error: ChatError) : RepositoryRequestStatus()
}
