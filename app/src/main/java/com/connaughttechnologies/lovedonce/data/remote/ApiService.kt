package com.connaughttechnologies.lovedonce.data.remote

import com.connaughttechnologies.lovedonce.data.models.requests.*
import com.connaughttechnologies.lovedonce.data.models.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {


    @POST(SIGN_UP)
    suspend fun signUp(@Body body: RequestSignUp): Response<ResponseSingUp>

    @POST(LOGIN)
    suspend fun login(@Body body: RequestLogin): Response<ResponseLogin>

    @POST(FORGOT_PASSWORD_REQUEST)
    suspend fun forgotPasswordRequest(@Body body: RequestForgotPassword): Response<ResponseForgotPasswordRequest>

    @POST(RESET_PASSWORD_OTP_VERIFIED)
    suspend fun resetPasswordOtpVerified(@Body body: RequestResetPasswordOtpVerified): Response<ResponseResetPasswordOtpVerified>

    @POST(RESET_PASSWORD_REQUEST)
    suspend fun resetPasswordRequest(@Body body: RequestResetPassword): Response<ResponseResetPassword>

    @POST(UPDATE_PROFILE)
    suspend fun updateProfile(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestUpdateProfile
    ): Response<ResponseUpdateProfile>

    @POST(PROFILE_DETAIL)
    suspend fun profileDetail(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestProfileDetail
    ): Response<ResponseProfileDetail>

    @POST(GET_ALL_SERVICES)
    suspend fun getAllServices(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetAllServices
    ): Response<ResponseGetAllServices>

    @POST(GET_SERVICES_DETAIL)
    suspend fun getServiceDetail(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetServiceDetail
    ): Response<ResponseServiceDetail>

    @POST(GET_STRIPE_INTENT)
    suspend fun getStripeIntent(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetServiceDetail
    ): Response<Any>

    @POST(CRATE_ORDER)
    suspend fun createOrder(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestCreateOrder
    ): Response<ResponseCreateOrder>

    @POST(ORDER_DETAIL)
    suspend fun orderDetail(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestOrderDetail
    ): Response<ResponseOrderDetail>

    @POST(ORDER_CANCEL)
    suspend fun orderCancel(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestOrderCancel
    ): Response<ResponseOrderDetail>

    @POST(ORDER_LISTING)
    suspend fun orderListing(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestOrderListing
    ): Response<ResponseOrderListing>

    @POST(ORDER_GET_SERVICE_TOKEN)
    suspend fun getServiceToken(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetServiceToken
    ): Response<ResponseGetServiceToken>

    @POST(UPDATE_ADDITIONAL_INFO)
    suspend fun updateAdditionalInfo(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestUpdateInfo
    ): Response<ResponseUpdateInfo>

    @POST(UPDATE_PAYMENT_STATUS)
    suspend fun updatePaymentStatus(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestUpdatePaymentStatus
    ): Response<ResponseUpdatePaymentStatus>

    @POST(GET_FAMILIES_EMAIL)
    suspend fun getFamilyEmail(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetFamilyEmail
    ): Response<ResponseGetFamilyEmail>

    @POST(UPDATE_FAMILIES_EMAIL)
    suspend fun updateFamilyEmail(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestUpdateFamilyEmail
    ): Response<ResponseUpdateFamilyEmail>

    @POST(GET_MY_MEMORIAL_PROFILES)
    suspend fun getMyMemorialProfiles(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetMyMemorialProfiles
    ): Response<ResponseGetMyMemorialProfiles>

    @POST(GET_MEMORIAL_PROFILES_DETAIL)
    suspend fun getMemorialProfilesDetail(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestGetMemorialProfileDetail
    ): Response<ResponseGetMemorialProfileResponse>

    @POST(ADD_MEMORIAL_PROFILE)
    suspend fun addMemorialProfile(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestAddMemorialProfile
    ): Response<ResponseAddMemorialProfile>

    @POST(SEARCH_MEMORIAL_PROFILE)
    suspend fun searchMemorialProfile(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestSearchMemorialProfile
    ): Response<ResponseSearchMemorialProfile>

    @POST(SET_REMINDER)
    suspend fun setReminder(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestSetReminder
    ): Response<ResponseSetReminder>

    @POST(GET_DIARY_DATA)
    suspend fun getDiaryData(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestDiaryData
    ): Response<ResponseDiaryData>

    @POST(UPDATE_DIARY_DATA)
    suspend fun updateDiaryData(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestUpdateDiaryData
    ): Response<ResponseUpdateDiaryData>

    @POST(ADD_DIARY_DATA)
    suspend fun addDiaryData(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestUpdateDiaryData
    ): Response<ResponseUpdateDiaryData>

    @POST(GET_ALL_DIARIES_DATA)
    suspend fun getMyDiaries(
        @Query(PARAM_API_TOKEN) token: String,
        @Body body: RequestMyDiaries
    ): Response<ResponseAllDiariesData>

    @POST(IMAGE_UPLOADING)
    @Multipart
    suspend fun imageUploading(
        @Part("type") type: RequestBody,
        @Query(PARAM_API_TOKEN) token: String,
        @Part image: MultipartBody.Part
    ): Response<ResponseImageUploading>

    @GET(GEO_CODE)
    suspend fun geoCode(
        @Query(PARAM_LATLNG) latLng: String,
        @Query(PARAM_GOOGLE_API_KEY) key: String,
    ): Response<ResponseGeoCode>

    companion object {
        const val API_V = "/api/v1"
        const val PARAM_API_TOKEN = "api_token"
        const val PARAM_LATLNG = "latlng"
        const val PARAM_GOOGLE_API_KEY = "key"
        const val SIGN_UP = "$API_V/signup"
        const val LOGIN = "$API_V/login"
        const val FORGOT_PASSWORD_REQUEST = "$API_V/forgot-password-request"
        const val RESET_PASSWORD_OTP_VERIFIED = "$API_V/reset-pass-otp-verified"
        const val RESET_PASSWORD_REQUEST = "$API_V/reset-password-request"
        const val UPDATE_PROFILE = "$API_V/update-profile"
        const val PROFILE_DETAIL = "$API_V/profile-detail"
        const val GET_ALL_SERVICES = "$API_V/get-all-services"
        const val GET_SERVICES_DETAIL = "$API_V/get-service-detail"
        const val GET_STRIPE_INTENT = "$API_V/get-stripe-intent"
        const val CRATE_ORDER = "$API_V/create-order"
        const val ORDER_DETAIL = "$API_V/order-detail"
        const val ORDER_CANCEL = "$API_V/cancel-order"
        const val ORDER_LISTING = "$API_V/order-listing"
        const val ORDER_GET_SERVICE_TOKEN = "$API_V/get-service-token"
        const val UPDATE_ADDITIONAL_INFO = "$API_V/update-additional-info"
        const val UPDATE_PAYMENT_STATUS = "$API_V/update-android-inapp-purchase"
        const val GET_FAMILIES_EMAIL = "$API_V/get-families-email"
        const val UPDATE_FAMILIES_EMAIL = "$API_V/update-families-email"
        const val GET_MY_MEMORIAL_PROFILES = "$API_V/get-my-memorial-profiles"
        const val GET_MEMORIAL_PROFILES_DETAIL = "$API_V/get-memorial-profile-detail"
        const val ADD_MEMORIAL_PROFILE = "$API_V/add-memorial-profile"
        const val SEARCH_MEMORIAL_PROFILE = "$API_V/search-memorial-profile"
        const val SET_REMINDER = "$API_V/set-reminder"
        const val GET_DIARY_DATA = "$API_V/get-diary-data"
        const val UPDATE_DIARY_DATA = "$API_V/update-diary-data"
        const val ADD_DIARY_DATA = "$API_V/add-diary-data"
        const val GET_ALL_DIARIES_DATA = "$API_V/get-all-diary-data"
        const val IMAGE_UPLOADING = "$API_V/image-uploading"
        const val GEO_CODE = "/maps/api/geocode/json"
    }
}