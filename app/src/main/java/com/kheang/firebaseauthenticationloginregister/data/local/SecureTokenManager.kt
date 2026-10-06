package com.kheang.firebaseauthenticationloginregister.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.text.get


@Singleton
class SecureTokenManager @Inject constructor(
    private val dataStore : DataStore<Preferences>,
    private val cryptoManager: CryptoManager
) {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }


    suspend fun saveToken(
        accessToken: String,
        refreshToken: String

    ) {

        val encryptedAccess = cryptoManager.encrypt(accessToken)
        val encryptedRefresh = cryptoManager.encrypt(refreshToken)


        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = encryptedAccess
            preferences[REFRESH_TOKEN] = encryptedRefresh
        }
    }


    val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN]?.let {
            cryptoManager.decrypt(it)
        }
    }

    val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN]?.let {
            cryptoManager.decrypt(it)
        }
    }

    suspend fun clearTokens() {
        dataStore.edit {
            it.remove(ACCESS_TOKEN)
            it.remove(REFRESH_TOKEN)
        }
    }
}