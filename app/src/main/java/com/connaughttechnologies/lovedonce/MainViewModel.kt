package com.connaughttechnologies.lovedonce

import androidx.lifecycle.viewModelScope
import com.connaughttechnologies.lovedonce.base.BaseViewModel
import com.connaughttechnologies.lovedonce.data.models.requests.*
import com.connaughttechnologies.lovedonce.data.models.responses.*
import com.connaughttechnologies.lovedonce.data.remote.Resource
import com.connaughttechnologies.lovedonce.utils.Constants
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    val eventResponseGetAllServices = SingleLiveEvent<List<Service>>()
    val eventResponseGetServiceDetail = SingleLiveEvent<Service>()
    val eventResponseCreateOrder = SingleLiveEvent<Any>()
    val eventResponseOrderDetail = SingleLiveEvent<OrderDetail>()
    val eventResponseOrderDetailFailed = SingleLiveEvent<Any>()
    val eventResponseOrderCancel = SingleLiveEvent<Any>()
    val eventResponseProfileDetail = SingleLiveEvent<User>()
    val eventResponseGetMyMemorialProfiles = SingleLiveEvent<ResponseGetMyMemorialProfiles>()
    val eventResponseSearchMemorialProfile = SingleLiveEvent<ResponseSearchMemorialProfile>()
    val eventResponseOrderListing = SingleLiveEvent<List<OrderListing>>()
    val eventResponseSuccessServiceToken = SingleLiveEvent<ResponseGetServiceToken>()
    val eventResponseUpdateAdditionalInfo = SingleLiveEvent<ResponseUpdateInfo>()
    val eventResponseGetDiaryData = SingleLiveEvent<ResponseDiaryData>()
    val eventResponseGetAllDiariesData = SingleLiveEvent<ResponseAllDiariesData>()
    val eventResponseImageUploading = SingleLiveEvent<ResponseImageUploading>()


    fun updateProfile(request: RequestUpdateProfile, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.updateProfile(
                    request.name,
                    request.password,
                    request.passwordConfirmation,
                    request.phoneNumber,
                    request.address, apiToken = apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                eventShowToast.value = message
                                dataLayer.saveUser(data.user)
                            }

                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getProfileDetail(apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getProfileDetails(
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                eventResponseProfileDetail.value = data.user
                            }

                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }


    fun getAllServices(apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getAllServices(
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                eventResponseGetAllServices.value = data
                            }

                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getServiceDetail(request: RequestGetServiceDetail, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getServiceDetail(
                    request.id,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                eventResponseGetServiceDetail.value = this
                            }
                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun createOrder(request: RequestCreateOrder, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.createOrder(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            showToast(message)
                            eventResponseCreateOrder.call()
                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun orderDetail(request: RequestOrderDetail, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.orderDetail(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            eventResponseOrderDetail.value = data
                        } else {
                            eventResponseOrderDetailFailed.call()
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun orderCancel(request: RequestOrderCancel, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.orderCancel(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            showToast(message)
                            eventResponseOrderCancel.call()
                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun orderListing(request: RequestOrderListing, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.orderListing(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                eventResponseOrderListing.value = data
                            }
                        } else {
                            showApiErrorMessage(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getServiceToken(request: RequestGetServiceToken, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getServiceToken(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            if (code != Constants.SUCCESS) {
                                showToast(message)
                            }
                        } else
                            eventResponseSuccessServiceToken.value = this
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun updateAdditionalInfo(request: RequestUpdateInfo, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.updateAdditionalInfo(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            showToast(message)
                        }
                        eventResponseUpdateAdditionalInfo.value = this
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }


    fun updatePaymentStatus(request: RequestUpdatePaymentStatus, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.updatePaymentStatus(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            showToast(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getFamilyEmail(request: RequestGetFamilyEmail, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getFamilyEmail(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            if (code != Constants.SUCCESS) {
                                showToast(message)
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun updateFamilyEmail(request: RequestUpdateFamilyEmail, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.updateFamilyEmail(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            if (code != Constants.SUCCESS) {
                                showToast(message)
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getMyMemorialProfiles(request: RequestGetMyMemorialProfiles, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getMyMemorialProfiles(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            if (code != Constants.SUCCESS) {
                                showToast(message)
                            } else {
                                eventResponseGetMyMemorialProfiles.value = this
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getMemorialProfileDetail(request: RequestGetMemorialProfileDetail, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getMemorialProfileDetail(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            if (code != Constants.SUCCESS) {
                                showToast(message)
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun addMemorialProfile(request: RequestAddMemorialProfile, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.addMemorialProfile(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            showToast(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun searchMemorialProfile(request: RequestSearchMemorialProfile, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.searchMemorialProfile(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            if (code != Constants.SUCCESS) {
                                showToast(message)
                            } else {
                                eventResponseSearchMemorialProfile.value = this
                            }
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun setReminder(request: RequestSetReminder, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.setReminder(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            showToast(message)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun getDiaryData(request: RequestDiaryData, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getDiaryData(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            eventResponseGetDiaryData.value = this
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun updateDiaryData(request: RequestUpdateDiaryData, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.updateDiaryData(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        showToast(message)
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun geyMyDiaries(request: RequestMyDiaries, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.getMyDiaries(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            eventResponseGetAllDiariesData.value = this
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun imageUploading(request: RequestImageUploading, apiToken: String) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.imageUploading(
                    request,
                    apiToken
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code != null) {
                            showToast(message)
                            eventResponseImageUploading.value = this
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    showToast(Constants.NETWORK_ERROR)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataLayer.logout()
        }
    }

}