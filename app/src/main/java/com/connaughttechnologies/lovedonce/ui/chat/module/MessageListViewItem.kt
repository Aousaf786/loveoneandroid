package com.connaughttechnologies.lovedonce.ui.chat.module

import android.net.Uri

data class MessageListViewItem(
    val sid: String,
    val uuid: String,
    val index: Long,
    val author: String,
    val body: String,
    val dateCreated: String,
    val sendStatus: SendStatus,
    val mediaSid: String?,
    val mediaFileName: String?,
    val mediaType: String?,
    val mediaSize: Long?,
    val mediaUri: Uri?,
    val mediaDownloadId: Long?,
    val mediaDownloadedBytes: Long?,
    val mediaDownloading: Boolean,
    val mediaUploading: Boolean,
    val mediaUploadedBytes: Long?,
    val mediaUploadUri: Uri?
) {

    fun isDownloaded() = mediaUri != null && mediaDownloadedBytes == mediaSize
}