package com.kheang.firebaseauthenticationloginregister.repository

import com.kheang.firebaseauthenticationloginregister.data.models.product.Product
import com.kheang.firebaseauthenticationloginregister.data.remote.ApiResult
import com.kheang.firebaseauthenticationloginregister.data.remote.DummyApi
import javax.inject.Inject

class RepositoryProduct @Inject constructor(
    private val dummyApi: DummyApi
) {
//    suspend fun getProducts() = productApi.getProducts()

    suspend fun getProducts(): ApiResult<Product>{
        return try {
            val response = dummyApi.getProducts()

            if (response.isSuccessful && response.body() != null){
                ApiResult.Success(response.body()!!)
            }else{
                ApiResult.Error("Error: ${response.code()} - ${response.message()}")
            }
        }catch (e: Exception){
            ApiResult.Error("NetWork Error: ${e.message ?: "Unknow error"}")
        }
    }

}