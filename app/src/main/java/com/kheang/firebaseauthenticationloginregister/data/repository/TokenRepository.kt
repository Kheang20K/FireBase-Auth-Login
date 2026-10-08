package com.kheang.firebaseauthenticationloginregister.data.repository

import kotlinx.coroutines.flow.first
import javax.inject.Inject

//class TokenRepository @Inject constructor(
//    private val dataStoreManager: TokenDataStoreManager
//){
//
//    suspend fun getAccessToken(): String? {
//        return dataStoreManager.accessToken.first()
//    }
//
//    suspend fun getRefreshToken(): String? {
//        return dataStoreManager.refreshToken.first()
//    }
//
//
//    suspend fun login(
//        accessToken : String,
//        refreshToken : String
//    ){
//        dataStoreManager.saveToken(
//            accessToken,
//            refreshToken
//        )
//    }
//
//    suspend fun clearToken(){
//        dataStoreManager.clearTokens()
//    }
//}