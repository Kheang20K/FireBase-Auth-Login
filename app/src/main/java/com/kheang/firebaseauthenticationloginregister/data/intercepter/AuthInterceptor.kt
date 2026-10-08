package com.kheang.firebaseauthenticationloginregister.data.intercepter

import com.kheang.firebaseauthenticationloginregister.data.local.SecureTokenManager
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
        val originalRequest = chain.request()
        if (!accessToken.isNullOrBlank()) {
            val request = originalRequest
                .newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()
            return chain.proceed(request)
        }
        return chain.proceed(originalRequest)
    }
}