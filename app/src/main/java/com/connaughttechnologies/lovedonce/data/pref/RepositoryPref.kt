package com.connaughttechnologies.lovedonce.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.connaughttechnologies.lovedonce.data.models.responses.User
import com.connaughttechnologies.lovedonce.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.KeyFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DATA_STORE_NAME)

class RepositoryPref(val context: Context) {

    private val KEY_API_KEY = stringPreferencesKey(NAME_API_KEY)
    private val KEY_FCM_TOKEN = stringPreferencesKey(NAME_FCM_TOKEN)
    private val KEY_NAME = stringPreferencesKey(NAME_NAME)
    private val KEY_EMAIL = stringPreferencesKey(NAME_EMAIL)
    private val KEY_PHONE = stringPreferencesKey(NAME_PHONE)
    private val KEY_ADDRESS = stringPreferencesKey(NAME_ADDRESS)
    private val KEY_ADDITIONAL_INFO = stringPreferencesKey(NAME_ADDITIONAL_INFO)
    private val KEY_SUBSCRIPTION_PACKAGE = stringPreferencesKey(NAME_SUBSCRIPTION_PACKAGE)
    private val KEY_subscription_status = intPreferencesKey(subscription_status)
    private val KEY_family_subscription_status = intPreferencesKey(family_subscription_status)
    private val KEY_IS_LOGGED_IN = booleanPreferencesKey(NAME_IS_LOGGED_IN)
    private val KEY_SHOULD_SHOW_ONBOARDING = booleanPreferencesKey(NAME_SHOULD_SHOW_ON_BOARDING)


    suspend fun saveApiKey(key: String) {
        context.dataStore.edit {
            it[KEY_API_KEY] = key
        }
    }

    val apiKey: Flow<String> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[KEY_API_KEY] ?: ""
        }

    suspend fun saveFcmToken(token: String) {
        context.dataStore.edit {
            it[KEY_FCM_TOKEN] = token
        }
    }

    val fcmToken: Flow<String> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            preferences[KEY_FCM_TOKEN] ?: ""
        }

    suspend fun saveUser(user: User) {
        context.dataStore.edit {
            it[KEY_NAME] = user.name
            it[KEY_EMAIL] = user.email
            it[KEY_PHONE] = user.phoneNumber
            it[KEY_ADDRESS] = user.address
            it[KEY_subscription_status] = user.subscriptionStatus
            it[KEY_family_subscription_status] = user.familySubscriptionStatus
            it[KEY_ADDITIONAL_INFO] = user.additionalInfo
            it[KEY_SUBSCRIPTION_PACKAGE] = user.subscriptionPackage ?: ""
        }
    }

    val user: Flow<User> = context.dataStore.data
        .map { preferences ->
            // No type safety.
            User(
                preferences[KEY_NAME] ?: "",
                preferences[KEY_NAME] ?: "",
                preferences[KEY_PHONE] ?: "",
                preferences[KEY_ADDRESS] ?: "",
                "",
                preferences[KEY_subscription_status] ?: 0,
                preferences[KEY_family_subscription_status] ?: 0,
                additionalInfo = preferences[KEY_ADDITIONAL_INFO] ?: "",
                subscriptionPackage = preferences[KEY_SUBSCRIPTION_PACKAGE]
            )
        }

    suspend fun saveIsLoggedIn(value: Boolean) {
        context.dataStore.edit {
            it[KEY_IS_LOGGED_IN] = value
        }
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            user
            // No type safety.
            preferences[KEY_IS_LOGGED_IN] ?: false
        }

    suspend fun saveShouldShowOnBoarding(value: Boolean) {
        context.dataStore.edit {
            it[KEY_SHOULD_SHOW_ONBOARDING] = value
        }
    }

    val shouldShowOnBoarding: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            user
            // No type safety.
            preferences[KEY_SHOULD_SHOW_ONBOARDING] ?: true
        }

    suspend fun clear() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        const val NAME_API_KEY = "NAME_API_KEY"
        const val NAME_FCM_TOKEN = "NAME_FCM_TOKEN"
        const val NAME_IS_LOGGED_IN = "NAME_IS_LOGGED_IN"
        const val NAME_NAME = "NAME_NAME"
        const val NAME_EMAIL = "NAME_EMAIL"
        const val NAME_PHONE = "NAME_PHONE"
        const val NAME_ADDRESS = "NAME_ADDRESS"
        const val NAME_ADDITIONAL_INFO = "NAME_ADDITIONAL_INFO"
        const val NAME_SUBSCRIPTION_PACKAGE = "SUBSCRIPTION_PACKAGE"
        const val subscription_status = "subscription_status"
        const val family_subscription_status = "family_subscription_status"
        const val NAME_SHOULD_SHOW_ON_BOARDING = "NAME_SHOULD_SHOW_ON_BOARDING"
    }
}