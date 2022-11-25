package com.connaughttechnologies.lovedonce.data.remote

import com.connaughttechnologies.lovedonce.data.models.requests.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RepositoryRemote(val apiService: ApiService) {

    suspend fun signUp(body: RequestSignUp) = apiService.signUp(body)

    suspend fun login(body: RequestLogin) = apiService.login(body)

    suspend fun forgotPasswordRequest(body: RequestForgotPassword) =
        apiService.forgotPasswordRequest(body)

    suspend fun resetPasswordOtpVerified(body: RequestResetPasswordOtpVerified) =
        apiService.resetPasswordOtpVerified(body)

    suspend fun resetPassword(body: RequestResetPassword) =
        apiService.resetPasswordRequest(body)

    suspend fun updateProfile(body: RequestUpdateProfile, token: String) =
        apiService.updateProfile(token, body)

    suspend fun getProfileDetail(body: RequestProfileDetail, token: String) =
        apiService.profileDetail(token, body)

    suspend fun getAllServices(body: RequestGetAllServices, token: String) =
        apiService.getAllServices(token, body)

    suspend fun getServiceDetail(body: RequestGetServiceDetail, token: String) =
        apiService.getServiceDetail(token, body)

    suspend fun getStripeIntent(body: RequestGetServiceDetail, token: String) =
        apiService.getStripeIntent(token, body)

    suspend fun createOrder(body: RequestCreateOrder, token: String) =
        apiService.createOrder(token, body)

    suspend fun orderDetail(body: RequestOrderDetail, token: String) =
        apiService.orderDetail(token, body)

    suspend fun orderCancel(body: RequestOrderCancel, token: String) =
        apiService.orderCancel(token, body)

    suspend fun orderListing(body: RequestOrderListing, token: String) =
        apiService.orderListing(token, body)

    suspend fun getServiceToken(body: RequestGetServiceToken, token: String) =
        apiService.getServiceToken(token, body)

    suspend fun updateAdditionalInfo(body: RequestUpdateInfo, token: String) =
        apiService.updateAdditionalInfo(token, body)

    suspend fun updatePaymentStatus(body: RequestUpdatePaymentStatus, token: String) =
        apiService.updatePaymentStatus(token, body)

    suspend fun getFamilyEmail(body: RequestGetFamilyEmail, token: String) =
        apiService.getFamilyEmail(token, body)

    suspend fun updateFamilyEmail(body: RequestUpdateFamilyEmail, token: String) =
        apiService.updateFamilyEmail(token, body)

    suspend fun getMyMemorialProfiles(body: RequestGetMyMemorialProfiles, token: String) =
        apiService.getMyMemorialProfiles(token, body)

    suspend fun getMemorialProfileDetail(body: RequestGetMemorialProfileDetail, token: String) =
        apiService.getMemorialProfilesDetail(token, body)

    suspend fun addMemorialProfile(body: RequestAddMemorialProfile, token: String) =
        apiService.addMemorialProfile(token, body)

    suspend fun searchMemorialProfile(body: RequestSearchMemorialProfile, token: String) =
        apiService.searchMemorialProfile(token, body)

    suspend fun setReminder(body: RequestSetReminder, token: String) =
        apiService.setReminder(token, body)

    suspend fun getDiaryData(body: RequestDiaryData, token: String) =
        apiService.getDiaryData(token, body)

    suspend fun updateDiaryData(body: RequestUpdateDiaryData, token: String) =
        apiService.updateDiaryData(token, body)

    suspend fun addDiaryData(body: RequestUpdateDiaryData, token: String) =
        apiService.addDiaryData(token, body)

    suspend fun getMyDiaries(body: RequestMyDiaries, token: String) =
        apiService.getMyDiaries(token, body)

    suspend fun imageUploading(type: RequestBody, part: MultipartBody.Part, token: String) =
        apiService.imageUploading(type, token, part)

    /**
     * latLng string format 29.390370,71.714103
     */
    suspend fun geoCode(latLng: String, key: String) =
        apiService.geoCode(latLng, key)
}