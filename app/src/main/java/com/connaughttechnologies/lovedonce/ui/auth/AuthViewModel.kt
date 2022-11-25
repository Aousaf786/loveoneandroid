package com.connaughttechnologies.lovedonce.ui.auth

import androidx.lifecycle.viewModelScope
import com.connaughttechnologies.lovedonce.base.BaseViewModel
import com.connaughttechnologies.lovedonce.data.DataLayer
import com.connaughttechnologies.lovedonce.data.models.requests.*
import com.connaughttechnologies.lovedonce.data.models.responses.OtpVerifiedData
import com.connaughttechnologies.lovedonce.data.models.responses.Service
import com.connaughttechnologies.lovedonce.data.remote.Resource
import com.connaughttechnologies.lovedonce.utils.Constants
import com.connaughttechnologies.lovedonce.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class AuthViewModel : BaseViewModel() {

    val eventSuccessApiForgotPassword = SingleLiveEvent<Any>()
    val eventResetPasswordOtpVerifiedSuccess = SingleLiveEvent<OtpVerifiedData>()
    val eventResetPasswordSuccess = SingleLiveEvent<Any>()

    fun signUp(it: RequestSignUp) {
        viewModelScope.launch {
            showLoading()
            val response = dataLayer.signUp(
                it.name,
                it.email,
                it.password,
                it.passwordConfirmation,
                it.fcmToken,
                it.phoneNumber,
                it.address
            )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                dataLayer.saveApiKey(user.apiToken)
                                dataLayer.saveUser(user)
                                dataLayer.saveIsLoggedIn(true)
                                eventNavigateMain.call()
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

    fun signIn(requestLogin: RequestLogin) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.login(requestLogin.email, requestLogin.password, requestLogin.fcmToken)
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            data?.apply {
                                dataLayer.saveApiKey(user.apiToken)
                                dataLayer.saveUser(user)
                                dataLayer.saveIsLoggedIn(true)
                                eventNavigateMain.call()
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

    fun forgotPassword(requestLogin: RequestForgotPassword) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.forgotPasswordRequest(requestLogin.email)
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            showToast(message)
                            eventSuccessApiForgotPassword.call()
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

    fun resetPasswordOtpVerify(requestLogin: RequestResetPasswordOtpVerified) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.resetPasswordOtpVerified(requestLogin)
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            eventShowToast.value = message
                            eventResetPasswordOtpVerifiedSuccess.value = data
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

    fun resetPassword(request: RequestResetPassword) {
        viewModelScope.launch {
            showLoading()
            val response =
                dataLayer.resetPassword(
                    request.token,
                    request.password,
                    request.passwordConfirmation
                )
            hideLoading()
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.apply {
                        if (code == Constants.SUCCESS) {
                            eventShowToast.value = message
                            eventResetPasswordSuccess.call()
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

}