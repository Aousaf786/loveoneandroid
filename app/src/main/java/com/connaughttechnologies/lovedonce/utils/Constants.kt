package com.connaughttechnologies.lovedonce.utils

import androidx.annotation.IntDef

class Constants {

    companion object {
        const val BASE_URL = "http://portal.yourloveones.com"
        const val GOOGLE_APIS = "https://maps.googleapis.com"
        const val NETWORK_ERROR = "Something Went Wrong! Please try again later."
        const val LOCATION_ERROR = "Could not find location. Please check your location settings"
        const val LOCATION_ADDRESS_ERROR =
            "Could not find address of your location. Please adjust pin little bit"

        const val DATA_STORE_NAME = "lovedonce"

        const val DELAY_SPLASH = 2_000L


        @IntDef(ERROR, SUCCESS, VALIDATION_ERROR, AUTHENTICATION_FAILED)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ResponseCode

        const val ERROR = 0
        const val SUCCESS = 1
        const val VALIDATION_ERROR = 2
        const val AUTHENTICATION_FAILED = 3
    }
}