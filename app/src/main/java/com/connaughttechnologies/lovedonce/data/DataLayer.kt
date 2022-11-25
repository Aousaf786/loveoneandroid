package com.connaughttechnologies.lovedonce.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.connaughttechnologies.lovedonce.R
import com.connaughttechnologies.lovedonce.data.models.requests.*
import com.connaughttechnologies.lovedonce.data.models.responses.*
import com.connaughttechnologies.lovedonce.data.pref.RepositoryPref
import com.connaughttechnologies.lovedonce.data.pref.dataStore
import com.connaughttechnologies.lovedonce.data.remote.RepositoryRemote
import com.connaughttechnologies.lovedonce.data.remote.Resource
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import java.lang.Exception

class DataLayer(
    private val repositoryRemote: RepositoryRemote,
    private val repositoryPref: RepositoryPref,
    private val context: Context
) {


    suspend fun saveApiKey(key: String) {
        repositoryPref.saveApiKey(key)
    }

    fun getApiKey() = repositoryPref.apiKey

    suspend fun saveFcmToken(key: String) {
        repositoryPref.saveFcmToken(key)
    }

    fun getFcmToken() = repositoryPref.fcmToken


    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        fcmToken: String,
        phoneNumber: String,
        address: String
    ): Resource<ResponseSingUp?> {
        return try {
            val response =
                repositoryRemote.signUp(
                    RequestSignUp(
                        name,
                        email,
                        password,
                        passwordConfirmation,
                        fcmToken,
                        phoneNumber,
                        address
                    )
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun login(
        email: String,
        password: String,
        fcmToken: String
    ): Resource<ResponseLogin?> {
        return try {
            val response =
                repositoryRemote.login(
                    RequestLogin(
                        email,
                        password,
                        fcmToken
                    )
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun forgotPasswordRequest(
        email: String
    ): Resource<ResponseForgotPasswordRequest?> {
        return try {
            val response =
                repositoryRemote.forgotPasswordRequest(
                    RequestForgotPassword(
                        email
                    )
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun resetPasswordOtpVerified(
        request: RequestResetPasswordOtpVerified
    ): Resource<ResponseResetPasswordOtpVerified?> {
        return try {
            val response =
                repositoryRemote.resetPasswordOtpVerified(
                    request
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun resetPassword(
        token: String, password: String, passwordConfirmation: String
    ): Resource<ResponseResetPassword?> {
        return try {
            val response =
                repositoryRemote.resetPassword(
                    RequestResetPassword(
                        token,
                        password,
                        passwordConfirmation
                    )
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun updateProfile(
        name: String,
        password: String,
        passwordConfirmation: String,
        phoneNumber: String,
        address: String,
        apiToken: String
    ): Resource<ResponseUpdateProfile?> {
        return try {
            val response =
                repositoryRemote.updateProfile(
                    RequestUpdateProfile(
                        name,
                        password,
                        passwordConfirmation,
                        phoneNumber,
                        address
                    ), apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getProfileDetails(
        apiToken: String
    ): Resource<ResponseProfileDetail?> {
        return try {
            val response =
                repositoryRemote.getProfileDetail(
                    RequestProfileDetail(), apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getAllServices(
        apiToken: String
    ): Resource<ResponseGetAllServices?> {
        return try {
            val response =
                repositoryRemote.getAllServices(
                    RequestGetAllServices(
                    ), apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getServiceDetail(
        id: Int,
        apiToken: String
    ): Resource<ResponseServiceDetail?> {
        return try {
            val response =
                repositoryRemote.getServiceDetail(
                    RequestGetServiceDetail(
                        id
                    ), apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getStripeIntent(
        id: Int,
        apiToken: String
    ): Resource<Any?> {
        return try {
            val response =
                repositoryRemote.getStripeIntent(
                    RequestGetServiceDetail(
                        id
                    ), apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun createOrder(
        request: RequestCreateOrder,
        apiToken: String
    ): Resource<ResponseCreateOrder?> {
        return try {
            val response =
                repositoryRemote.createOrder(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun orderDetail(
        request: RequestOrderDetail,
        apiToken: String
    ): Resource<ResponseOrderDetail?> {
        return try {
            val response =
                repositoryRemote.orderDetail(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun orderCancel(
        request: RequestOrderCancel,
        apiToken: String
    ): Resource<ResponseOrderDetail?> {
        return try {
            val response =
                repositoryRemote.orderCancel(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun orderListing(
        request: RequestOrderListing,
        apiToken: String
    ): Resource<ResponseOrderListing?> {
        return try {
            val response =
                repositoryRemote.orderListing(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getServiceToken(
        request: RequestGetServiceToken,
        apiToken: String
    ): Resource<ResponseGetServiceToken?> {
        return try {
            val response =
                repositoryRemote.getServiceToken(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun updateAdditionalInfo(
        request: RequestUpdateInfo,
        apiToken: String
    ): Resource<ResponseUpdateInfo?> {
        return try {
            val response =
                repositoryRemote.updateAdditionalInfo(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun updatePaymentStatus(
        request: RequestUpdatePaymentStatus,
        apiToken: String
    ): Resource<ResponseUpdatePaymentStatus?> {
        return try {
            val response =
                repositoryRemote.updatePaymentStatus(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getFamilyEmail(
        request: RequestGetFamilyEmail,
        apiToken: String
    ): Resource<ResponseGetFamilyEmail?> {
        return try {
            val response =
                repositoryRemote.getFamilyEmail(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun updateFamilyEmail(
        request: RequestUpdateFamilyEmail,
        apiToken: String
    ): Resource<ResponseUpdateFamilyEmail?> {
        return try {
            val response =
                repositoryRemote.updateFamilyEmail(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getMyMemorialProfiles(
        request: RequestGetMyMemorialProfiles,
        apiToken: String
    ): Resource<ResponseGetMyMemorialProfiles?> {
        return try {
            val response =
                repositoryRemote.getMyMemorialProfiles(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getMemorialProfileDetail(
        request: RequestGetMemorialProfileDetail,
        apiToken: String
    ): Resource<ResponseGetMemorialProfileResponse?> {
        return try {
            val response =
                repositoryRemote.getMemorialProfileDetail(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun addMemorialProfile(
        request: RequestAddMemorialProfile,
        apiToken: String
    ): Resource<ResponseAddMemorialProfile?> {
        return try {
            val response =
                repositoryRemote.addMemorialProfile(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun searchMemorialProfile(
        request: RequestSearchMemorialProfile,
        apiToken: String
    ): Resource<ResponseSearchMemorialProfile?> {
        return try {
            val response =
                repositoryRemote.searchMemorialProfile(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun setReminder(
        request: RequestSetReminder,
        apiToken: String
    ): Resource<ResponseSetReminder?> {
        return try {
            val response =
                repositoryRemote.setReminder(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getDiaryData(
        request: RequestDiaryData,
        apiToken: String
    ): Resource<ResponseDiaryData?> {
        return try {
            val response =
                repositoryRemote.getDiaryData(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun updateDiaryData(
        request: RequestUpdateDiaryData,
        apiToken: String
    ): Resource<ResponseUpdateDiaryData?> {
        return try {
            val response = if (request.id == null) {
                repositoryRemote.addDiaryData(
                    request, apiToken
                )
            } else
                repositoryRemote.updateDiaryData(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun getMyDiaries(
        request: RequestMyDiaries,
        apiToken: String
    ): Resource<ResponseAllDiariesData?> {
        return try {
            val response =
                repositoryRemote.getMyDiaries(
                    request, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun imageUploading(
        request: RequestImageUploading,
        apiToken: String
    ): Resource<ResponseImageUploading?> {
        return try {
            val response =
                repositoryRemote.imageUploading(
                    request.type, request.image, apiToken
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun geoCode(
        latlng: String,
        key: String
    ): Resource<ResponseGeoCode?> {
        return try {
            val response =
                repositoryRemote.geoCode(
                    latlng, key
                )
            processResponse(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.error(context.resources.getString(R.string.str_api_error))
        }
    }

    suspend fun saveUser(user: User) {
        repositoryPref.saveUser(user)
    }

    fun getUser() = repositoryPref.user

    suspend fun saveIsLoggedIn(value: Boolean) {
        repositoryPref.saveIsLoggedIn(value)
    }

    fun getIsLoggedIn() = repositoryPref.isLoggedIn

    fun shouldShowOnBoarding() = repositoryPref.shouldShowOnBoarding

    suspend fun saveShouldShowOnBoarding(value: Boolean) {
        repositoryPref.saveShouldShowOnBoarding(value)
    }

    suspend fun logout() {
        repositoryPref.clear()
    }

    private fun <T> processResponse(response: Response<T>): Resource<T?> {
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(
                context.resources.getString(R.string.str_api_error),
                statusCode = response.code()
            )
        }
    }
}