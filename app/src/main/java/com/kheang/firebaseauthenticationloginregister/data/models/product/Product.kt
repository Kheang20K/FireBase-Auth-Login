package com.kheang.firebaseauthenticationloginregister.domain.models.product

data class Product(
    val limit: Int,
    val products: List<ProductX>,
    val skip: Int,
    val total: Int
)