package com.connaughttechnologies.lovedonce.extensions

fun Double.formatPrice():String{
    return String.format("£%.2f",this)
}