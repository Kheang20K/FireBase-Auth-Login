package com.kheang.firebaseauthenticationloginregister.data.models.product

data class Review(
    val comment: String,
    val date: String,
    val rating: Int,
    val reviewerEmail: String,
    val reviewerName: String
)