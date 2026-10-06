package com.kheang.firebaseauthenticationloginregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kheang.firebaseauthenticationloginregister.domain.database.CartItem
import com.kheang.firebaseauthenticationloginregister.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
): ViewModel() {

//    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
//    val cartItems = _cartItems
//
//    init {
//        viewModelScope.launch {
//            _cartItems.value = repository.getCartItems()
//        }
//    }
val cartItems: StateFlow<List<CartItem>> = repository.getCartItems()
    .stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun addToCart(item: CartItem){

        viewModelScope.launch {
            repository.addToCart(item)
        }
    }

    fun upDateCart(item: CartItem){
        viewModelScope.launch {
            repository.upDateFromCart(item)
        }
    }

    fun removeFromCart(item: CartItem){
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }
    fun clearCart(){
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}