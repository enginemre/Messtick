package com.messtick.app.core.data.persistent

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserDataStore(
    private val dataStore: DataStore<Preferences>,
) {
    private val _sessionOutFlow = MutableSharedFlow<Unit>()
    val sessionOutFlow: Flow<Unit> = _sessionOutFlow.asSharedFlow()

    suspend fun updateRefreshToken(refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN_KEY] = refreshToken
        }
    }

    suspend fun sendSessionOutEvent() {
        _sessionOutFlow.emit(Unit)
    }

    suspend fun updateAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = accessToken
        }
    }

    suspend fun userIsLogged() = accessToken.first().isNullOrEmpty().not()

    val accessToken: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }
    val refreshToken: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }

    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN_KEY)
            preferences.remove(REFRESH_TOKEN_KEY)
        }
    }

    companion object {
        val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

    }
}