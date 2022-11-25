package com.connaughttechnologies.lovedonce.extensions

import android.widget.EditText


fun EditText.getValue(): String {
    return this.text.toString().trim()
}

fun EditText.validate(): Boolean {
    return this.text.toString().trim().isNotEmpty()
}

fun EditText.validateEmail(): Boolean {
    return this.text.toString().trim().isNotEmpty() && this.text.toString().contains("@")
}

fun EditText.validatePhone(): Boolean {
    return this.text.toString().trim()
        .isNotEmpty() && this.text.toString().length >= 11 && this.text.toString().length <= 13
}

fun EditText.validateCvv(): Boolean {
    return this.text.toString().trim().isNotEmpty()
}

fun EditText.validateCard(): Boolean {
    return this.text.toString().trim().isNotEmpty() && this.text.toString().length == 16
}