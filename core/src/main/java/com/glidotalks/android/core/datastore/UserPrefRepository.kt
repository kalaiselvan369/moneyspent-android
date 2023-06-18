package com.glidotalks.android.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

private object PreferencesKeys {
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_CURRENCY = stringPreferencesKey("user_currency")
}

class UserPrefRepository(private val datastore: DataStore<Preferences>) {

    suspend fun saveUserName(name: String) {
        datastore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
        }
    }

    suspend fun saveUserCurrency(currency: String) {
        datastore.edit { preferences ->
            preferences[PreferencesKeys.USER_CURRENCY] = currency
        }
    }

    fun isOnboardingCompleted(): Flow<Boolean> =
        datastore.data.map { preferences ->
            val result = !preferences[PreferencesKeys.USER_CURRENCY].isNullOrBlank()
            Timber.d("currency %s", result)
            result
        }
}