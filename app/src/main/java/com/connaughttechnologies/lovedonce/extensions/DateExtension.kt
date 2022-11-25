package com.connaughttechnologies.lovedonce.extensions

import java.text.SimpleDateFormat
import java.util.*

private const val formatTime = "h:mm a"
private val sdfTime = SimpleDateFormat(formatTime)

fun Date.toFormatTime():String{
    return sdfTime.format(this)
}