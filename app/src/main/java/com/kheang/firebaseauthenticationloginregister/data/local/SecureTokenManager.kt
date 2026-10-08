package com.kheang.firebaseauthenticationloginregister.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kheang.firebaseauthenticationloginregister.data.security.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


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

        Log.d("SecureToken", "Encrypted access = $encryptedAccess")

        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = encryptedAccess
            preferences[REFRESH_TOKEN] = encryptedRefresh
        }
        Log.d("SecureToken", "Tokens saved")
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