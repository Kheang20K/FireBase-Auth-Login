package com.kheang.firebaseauthenticationloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kheang.firebaseauthenticationloginregister.domain.models.Product
import com.kheang.firebaseauthenticationloginregister.domain.models.ProductX
import com.kheang.firebaseauthenticationloginregister.domain.remote.ApiResult
import com.kheang.firebaseauthenticationloginregister.repository.RepositoryProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repositoryProduct: RepositoryProduct
): ViewModel(){
    private val _productState = MutableStateFlow<ApiResult<Product>>(ApiResult.Loading)
    val productState: StateFlow<ApiResult<Product>> = _productState

    init {
        loadProducts()
    }
    fun loadProducts() {
        viewModelScope.launch {
            _productState.value = ApiResult.Loading
            _productState.value = repositoryProduct.getProducts()


        }
    }
    fun filterByCategory(category: String): List<ProductX>{
        val allProducts = (_productState.value as? ApiResult.Success)?.data?.products ?:emptyList()
        return when (category.lowercase()) {
            "all" -> allProducts
            "drinks" -> allProducts.filter { it.category.contains("smartphone", ignoreCase = true) }
            "fruit" -> allProducts.filter { it.category.contains("groceries", ignoreCase = true) }
            "cosmetic" -> allProducts.filter {
                it.category.contains("fragrances", ignoreCase = true) ||
                        it.category.contains("skincare", ignoreCase = true)

            }
            "interior" -> allProducts.filter {
                it.category.contains("home-decoration", ignoreCase = true) ||
                        it.category.contains("furniture", ignoreCase = true)
            }


            else -> emptyList()
        }

    }

    fun getProductId(productId: Int): ProductX?{
        return (_productState.value as? ApiResult.Success)?.data?.products?.find { it.id == productId }
    }

    // ----------------------
    // Count products in a category
    // ----------------------
    fun countByCategory(category: String): Int {
        return (_productState.value as? ApiResult.Success)?.data?.products
            ?.count { it.category.equals(category, ignoreCase = true) } ?: 0
    }


}