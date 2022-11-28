package com.connaughttechnologies.lovedonce.base

import android.location.Location
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.BillingClient
import com.connaughttechnologies.lovedonce.data.models.requests.*
import com.connaughttechnologies.lovedonce.data.models.responses.*
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent

class SharedViewModel : BaseViewModel() {

    var fcmToken = ""
    lateinit var apiToken: String

    val eventSharedShowLoading = SingleLiveEvent<Boolean>()
    fun showSharedLoading() {
        eventSharedShowLoading.value = true
    }

    fun hideSharedLoading() {
        eventSharedShowLoading.value = false
    }

    //sign-up
    val eventExeSignUpApi = SingleLiveEvent<RequestSignUp>()

    //sign-in
    val eventExeApiSignIn = SingleLiveEvent<RequestLogin>()

    //forgot password
    val eventExeApiForgotPassword = SingleLiveEvent<RequestForgotPassword>()

    //reset password otp
    val eventExeApiResetPasswordOtpVerified = SingleLiveEvent<RequestResetPasswordOtpVerified>()
    var otpVerifiedToken = ""

    //reset password
    val eventExeApiResetPassword = SingleLiveEvent<RequestResetPassword>()

    //update profile
    val eventExeApiUpdateProfile = SingleLiveEvent<RequestUpdateProfile>()

    //profile detail
    val eventExeApiGetProfileDetail = SingleLiveEvent<RequestProfileDetail>()
    val liveProfileDetail = MutableLiveData<User>()

    //profiles
    val eventExeApiGetMyMemorialProfiles = SingleLiveEvent<RequestGetMyMemorialProfiles>()
    val eventExeApiSearchMemorialProfiles = SingleLiveEvent<RequestSearchMemorialProfile>()
    val liveMyMemorialProfiles = MutableLiveData<ResponseGetMyMemorialProfiles>()
    val liveSearchMemorialProfiles = MutableLiveData<ResponseSearchMemorialProfile>()
    var selectedProfile: MemorialProfile? = null

    //services
    val eventExeApiGetAllServices = SingleLiveEvent<RequestGetAllServices>()
    val liveListServices = MutableLiveData<List<Service>>()

    //service detail
    val eventExeApiGetServiceDetail = SingleLiveEvent<RequestGetServiceDetail>()
    var serviceId: Int = 0
    val liveServiceDetail = MutableLiveData<Service>()

    //schedule
    var orderTime: String = ""
    var orderTimeText: String = ""
    var orderDate: String = ""
    var immediate: Int = 0

    //map
    var lat: String = ""
    var lng: String = ""
    var address: String = ""

    //payment
    var cardNumber: String = ""
    var cardExpMonth: Int = 0
    var cardExpYear: Int = 0
    var cardCvv: String = ""
    val eventCreateOrderSuccess = SingleLiveEvent<Any>()


    //create order
    val eventExeApiCreateOrder = SingleLiveEvent<RequestCreateOrder>()

    //toolbar
    val eventToolbarBackedClicked = SingleLiveEvent<Any>()
    fun onClickToolbarBack() {
        eventToolbarBackedClicked.call()
    }
    val eventToolbarRightBtnClicked = SingleLiveEvent<Any>()
    fun onClickToolbarRightButton() {
        eventToolbarRightBtnClicked.call()
    }

    //app permissions
    val eventPermissionCheckLocation = SingleLiveEvent<Any>()
    val eventPermissionResultLocation = SingleLiveEvent<Boolean>()
    val eventGetLastKnownLocation = SingleLiveEvent<Any>()
    val liveLastKnownLocation = MutableLiveData<Location?>()

    //order detail
    val eventExeApiOrderDetail = SingleLiveEvent<RequestOrderDetail>()
    val eventExeApiOrderCancel = SingleLiveEvent<RequestOrderCancel>()
    val eventOrderDetailSuccess = SingleLiveEvent<OrderDetail>()
    val eventOrderDetailFailed = SingleLiveEvent<OrderDetail>()
    val eventOrderCancelSuccess = SingleLiveEvent<Any>()
    var orderId = 0

    //settings
    val eventLogout = SingleLiveEvent<Any>()

    //order listing
    val eventExeApiOrderListing = SingleLiveEvent<RequestOrderListing>()
    val eventSuccessApiOrderListing = SingleLiveEvent<ResponseOrderListing>()
    val liveOrderListing = MutableLiveData<List<OrderListing>>()

    //chat
    val eventExeApiGetServiceToken = SingleLiveEvent<RequestGetServiceToken>()
    val liveServiceToken = MutableLiveData<ResponseGetServiceToken>()
    var isGroupChat = false

    //subscription
    var isBillingClientReady = false
    lateinit var billingClient: BillingClient

    //additional info
    val eventExeApiUpdateAdditionalInfo = SingleLiveEvent<RequestUpdateInfo>()
    val eventApiResponseUpdateAdditionalInfo = SingleLiveEvent<ResponseUpdateInfo>()

    //reminder
    val eventExeApiSetReminder = SingleLiveEvent<RequestSetReminder>()
    val eventApiResponseSetReminder = SingleLiveEvent<ResponseSetReminder>()

    //diary
    var diaryId: Int? = null
    val eventExeApiGetDiaryData = SingleLiveEvent<RequestDiaryData>()
    val eventExeApiUpdateDiaryData = SingleLiveEvent<RequestUpdateDiaryData>()
    val eventApiResponseGetDiaryData = SingleLiveEvent<ResponseDiaryData>()

    //my diaries
    val eventExeApiMyDiaries = SingleLiveEvent<RequestMyDiaries>()
    val eventApiResponseGetAllDiariesData = SingleLiveEvent<ResponseAllDiariesData>()

    //memorial search
    var requestSearchMemorialProfile: RequestSearchMemorialProfile? = null

    //Add Profile
    var pickedImageUri = SingleLiveEvent<Uri>()
    val eventExeApiAddProfile = SingleLiveEvent<RequestAddMemorialProfile>()
    val eventApiResponseAddProfile = SingleLiveEvent<ResponseAddMemorialProfile>()
    val eventExeApiImageUploading = SingleLiveEvent<RequestImageUploading>()
    val eventApiResponseImageUploading = SingleLiveEvent<ResponseImageUploading>()

    //cart
    val listCart = mutableListOf<Service>()
    val liveListCart: MutableLiveData<MutableList<Service>> =
        MutableLiveData<MutableList<Service>>()
}