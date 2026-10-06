package com.kheang.firebaseauthenticationloginregister.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kheang.firebaseauthenticationloginregister.viewmodel.CartViewModel

@Composable
fun ButtonBadgeCart(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
){
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartCount = cartItems.size

    Column (
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ){
        BadgedBox(
            badge = {
                if (cartCount > 0 ){
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = cartCount.toString()
                        )
                    }
                }
            }
        ) {
            IconButton(
                onClick = {
                    navController.navigate("cart")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "shopping_card",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }


}