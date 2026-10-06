package com.kheang.firebaseauthenticationloginregister.data.models.user

data class AuthResponse (
    val token : String,
    val refreshToken: String? = null,
    val expiresIn: Long? = null

)