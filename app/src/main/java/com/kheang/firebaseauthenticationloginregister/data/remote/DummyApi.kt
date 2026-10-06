package com.kheang.firebaseauthenticationloginregister.domain.remote

import com.kheang.firebaseauthenticationloginregister.domain.models.product.Product
import com.kheang.firebaseauthenticationloginregister.domain.models.user.AuthResponse
import com.kheang.firebaseauthenticationloginregister.domain.models.user.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DummyApi {
    @GET("products")
//    suspend fun getProducts(): Product
    suspend fun getProducts(): Response<Product>



    @POST("auth/login")
    suspend fun getUser(
        @Body request : LoginRequest
    ): AuthResponse

}