package com.connaughttechnologies.lovedonce.ui.additionalinfo

import androidx.databinding.ObservableField
import com.connaughttechnologies.lovedonce.base.BaseViewModel
import com.connaughttechnologies.lovedonce.data.models.requests.RequestUpdateInfo
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent

class AdditionalInfoViewModel : BaseViewModel() {

    val obAdditionalInfo = ObservableField<String?>()
    val eventExeApi = SingleLiveEvent<RequestUpdateInfo>()

    fun addAdditionalInfo() {
        obAdditionalInfo.get()?.apply {
            if (isNotBlank()) {
                eventExeApi.value = RequestUpdateInfo(this)
            }
        }
    }
}