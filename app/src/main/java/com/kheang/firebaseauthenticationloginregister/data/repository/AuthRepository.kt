package com.kheang.firebaseauthenticationloginregister.data.repository

import com.kheang.firebaseauthenticationloginregister.data.local.SecureTokenManager
import com.kheang.firebaseauthenticationloginregister.data.remote.DummyApi
import com.kheang.firebaseauthenticationloginregister.data.remote.LoginUserAuth
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val userApi : DummyApi,
    private val tokenManager: SecureTokenManager
){

    suspend fun login(
        username: String,
        password : String
    ){
        val response = userApi.login(
            LoginUserAuth.LoginRequest(
                username = username,
                password = password
            )
        )
        tokenManager.saveToken(
            accessToken = response.accessToken,
            refreshToken = response.refreshToken
        )
    }

    suspend fun logout(){
        tokenManager.clearTokens()
    }

}