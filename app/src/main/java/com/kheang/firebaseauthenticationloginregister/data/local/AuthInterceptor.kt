package com.kheang.firebaseauthenticationloginregister.data.local

import com.kheang.firebaseauthenticationloginregister.data.repository.TokenRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: SecureTokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking {
            tokenManager.accessToken.first()
        }
        val originalRequest  = chain.request()
        if (!accessToken.isNullOrBlank()){
            return chain.proceed(originalRequest)
        }
        val request = originalRequest
            .newBuilder()
            .header("Authorization","Bearer $accessToken")
            .build()

        return chain.proceed(request)

    }
}