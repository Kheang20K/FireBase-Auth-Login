package com.kheang.firebaseauthenticationloginregister.data.intercepter

import android.util.Log
import com.kheang.firebaseauthenticationloginregister.data.local.SecureTokenManager
import com.kheang.firebaseauthenticationloginregister.data.remote.DummyApi
import com.kheang.firebaseauthenticationloginregister.data.remote.LoginUserAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider

class TokenAuthenticator @Inject constructor(
    private val authApiProvider: Provider<DummyApi>,
    private val tokenManager: SecureTokenManager,
): Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        // Avoid infinite refresh loops.
        if (responseCount(response) >= 2) {
            return null
        }

        val refreshToken = runBlocking {
            tokenManager.refreshToken.first()
        }

        if (refreshToken.isNullOrBlank()) {
            return null
        }

        return try {
            val refreshResponse = runBlocking {
                authApiProvider.get().refresh(
                    LoginUserAuth.RefreshRequest(
                        refreshToken = refreshToken,
                    )
                )
            }

            runBlocking {
                tokenManager.saveToken(
                    accessToken = refreshResponse.accessToken,
                    refreshToken = refreshResponse.refreshToken,
                )
            }
            response.request
                .newBuilder()
                .header(
                    "Authorization",
                    "Bearer ${refreshResponse.accessToken}",
                )
                .build()
        } catch (e: Exception) {
            runBlocking {
                tokenManager.clearTokens()
            }
            Log.d("Authorization", e.message ?: "Token refresh failed")
            null
        }
    }

    private fun responseCount(
        response: Response
    ): Int {

        var count = 1
        var prior = response.priorResponse

        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }


}