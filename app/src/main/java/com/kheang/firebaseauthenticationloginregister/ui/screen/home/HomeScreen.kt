package com.kheang.firebaseauthenticationloginregister.screen.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kheang.firebaseauthenticationloginregister.ui.components.AllCategory
import com.kheang.firebaseauthenticationloginregister.ui.components.ButtonBadgeCart
import com.kheang.firebaseauthenticationloginregister.ui.screen.page.AuthState
import com.kheang.firebaseauthenticationloginregister.ui.screen.page.AuthViewModel
import com.kheang.firebaseauthenticationloginregister.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    productViewModel: ProductViewModel = hiltViewModel(),
){
    val authState = viewModel.authState.observeAsState()
//    val productResponse by productViewModel.productState.collectAsState()



    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    LaunchedEffect(Unit) {
        productViewModel.loadProducts()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("🛍️ DummyJSON Products")
                },
                actions = {
                    ButtonBadgeCart(navController)
                }
            )
        }
    ) { padding ->
        LazyColumn  (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){

            item {
                Spacer(Modifier.height(16.dp))
            }
            item {
                AllCategory(navController = navController)
            }


        }
    }
}

