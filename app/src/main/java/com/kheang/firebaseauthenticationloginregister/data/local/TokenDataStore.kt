package com.kheang.firebaseauthenticationloginregister.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


private const val TOKEN_DATASTORE = "token_datastore"

val Context.tokenDataStore : DataStore<Preferences> by preferencesDataStore(
    name = TOKEN_DATASTORE
)