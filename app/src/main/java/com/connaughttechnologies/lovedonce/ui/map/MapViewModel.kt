package com.connaughttechnologies.lovedonce.ui.map

import android.location.Geocoder
import androidx.lifecycle.viewModelScope
import com.connaughttechnologies.lovedonce.base.BaseViewModel
import com.connaughttechnologies.lovedonce.utils.Constants
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import java.io.IOException
import kotlin.math.ln

class MapViewModel : BaseViewModel() {

    val eventAddress = SingleLiveEvent<String>()

    fun getAddressFromLocation(lat: Double, lng: Double, geoCoder: Geocoder) {
        viewModelScope.launch {
            getAddress(lat, lng, geoCoder)
        }
    }

    private suspend fun getAddress(lat: Double, lng: Double, geoCoder: Geocoder) {
        withContext(Dispatchers.IO) {
            try {
                val list = geoCoder.getFromLocation(lat, lng, 1)
                if (list.isNotEmpty()) {
                    val address = list.first()
                    withContext(Dispatchers.Main) {
                        eventAddress.value = address.getAddressLine(0)
                    }
                } else {
                    showToast(Constants.LOCATION_ADDRESS_ERROR)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    e.message?.let { showToast(Constants.LOCATION_ADDRESS_ERROR) }
                }
            }
        }
    }
}