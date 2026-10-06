package com.kheang.firebaseauthenticationloginregister.repository

import com.kheang.firebaseauthenticationloginregister.data.database.CartDao
import com.kheang.firebaseauthenticationloginregister.data.database.CartItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    fun getCartItems(): Flow<List<CartItem>> = cartDao.getAllCartItem()
    suspend fun addToCart(item: CartItem){
        cartDao.insertItem(item)
    }

    suspend fun upDateFromCart(item: CartItem){
        cartDao.upDateItem(item)
    }

    suspend fun removeFromCart(item: CartItem){
        cartDao.deleteItem(item)
    }
    suspend fun clearCart(){
        cartDao.clearCart()
    }

}