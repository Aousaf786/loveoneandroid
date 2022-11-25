package com.connaughttechnologies.lovedonce.base

import androidx.lifecycle.ViewModel
import com.connaughttechnologies.lovedonce.data.DataLayer
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent
import java.util.*

open class BaseViewModel : ViewModel() {

    lateinit var dataLayer: DataLayer
    val eventNavigateMain = SingleLiveEvent<Any>()
    val eventNavigateAuth = SingleLiveEvent<Any>()
    val eventShowToast = SingleLiveEvent<String>()
    val eventShowLoading = SingleLiveEvent<Boolean>()

    fun initDataLayer(dataLayer: DataLayer) {
        this.dataLayer = dataLayer
    }

    fun showLoading() {
        eventShowLoading.value = true
    }

    fun hideLoading() {
        eventShowLoading.value = false
    }

    fun showToast(message: String) {
        eventShowToast.value = message
    }

    fun showApiErrorMessage(message: String) {
        eventShowToast.value = message
    }

    fun getBirthYearData(): List<String> {
        val list = mutableListOf<String>()
        list.add("In")
        val cal = Calendar.getInstance()
        for (i in 1..100) {
            list.add(cal.get(Calendar.YEAR).toString())
            cal.add(Calendar.YEAR, -1)
        }
        return list
    }
}