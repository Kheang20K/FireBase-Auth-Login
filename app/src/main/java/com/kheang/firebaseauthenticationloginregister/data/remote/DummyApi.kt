package com.kheang.firebaseauthenticationloginregister.data.remote

import com.kheang.firebaseauthenticationloginregister.data.models.product.Product
import com.kheang.firebaseauthenticationloginregister.data.models.user.AuthResponse
import com.kheang.firebaseauthenticationloginregister.data.models.user.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DummyApi {
    @GET("products")
//    suspend fun getProducts(): Product
    suspend fun getProducts(): Response<Product>



    @POST("auth/login")
    suspend fun login(
        @Body request : LoginUserAuth.LoginRequest
    ): LoginUserAuth.LoginResponse

    @POST("auth/refresh")
    suspend fun refresh(
        @Body request: LoginUserAuth.RefreshRequest
    ): LoginUserAuth.RefreshResponse
}


object LoginUserAuth {
    data class LoginRequest(
        val username: String,
        val password: String
    )

    data class LoginResponse(
        val accessToken: String,
        val refreshToken: String
    )

    data class RefreshRequest(
        val refreshToken: String
    )

    data class RefreshResponse(
        val accessToken: String,
        val refreshToken: String
    )

}