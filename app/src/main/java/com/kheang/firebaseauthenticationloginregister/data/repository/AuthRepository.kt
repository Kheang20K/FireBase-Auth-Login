package com.kheang.firebaseauthenticationloginregister.data.repository

import com.kheang.firebaseauthenticationloginregister.data.remote.DummyApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val userApi : DummyApi,
    private val repository: TokenRepository
){

    suspend fun login(
        username: String,
        password : String
    ){
        val response = userApi.getUser()
    }

}