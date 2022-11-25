package com.connaughttechnologies.lovedonce.ui.chat.module


enum class SendStatus(val value: Int) {
    UNDEFINED(0),
    SENDING(1),
    SENT(2),
    ERROR(3);

    companion object {
        private val valuesMap = values().associateBy { it.value }
        fun fromInt(value: Int) = valuesMap[value] ?: error("Invalid value $value for SendStatus")
    }
}
