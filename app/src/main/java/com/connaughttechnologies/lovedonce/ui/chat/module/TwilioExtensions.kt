package com.connaughttechnologies.lovedonce.ui.chat.module

//import com.twilio.chat.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/*class ChatException(val error: ChatError) : Exception("$error") {
    constructor(errorInfo: ErrorInfo) : this(ChatError.fromErrorInfo(errorInfo))
}
suspend fun Messages.getLastMessages(count: Int): List<Message> = suspendCoroutine { continuation ->
    getLastMessages(count, object : CallbackListener<List<Message>>() {

        override fun onSuccess(result: List<Message>) = continuation.resume(result)

        override fun onError(errorInfo: ErrorInfo) = continuation.resumeWithException(ChatException(errorInfo))
    })
}

suspend fun Messages.getMessagesBefore(index: Long, count: Int): List<Message> = suspendCoroutine { continuation ->
    getMessagesBefore(index, count, object : CallbackListener<List<Message>>() {

        override fun onSuccess(result: List<Message>) = continuation.resume(result)

        override fun onError(errorInfo: ErrorInfo) = continuation.resumeWithException(ChatException(errorInfo))
    })
}

suspend fun Channels.getChannel(sidOrUniqueName: String): Channel = suspendCoroutine { continuation ->
    getChannel(sidOrUniqueName, object : CallbackListener<Channel>() {

        override fun onSuccess(result: Channel) = continuation.resume(result)

        override fun onError(errorInfo: ErrorInfo) = continuation.resumeWithException(ChatException(errorInfo))
    })
}*/
