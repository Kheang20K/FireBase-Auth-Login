package com.kheang.firebaseauthenticationloginregister

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kheang.firebaseauthenticationloginregister.page.LoginPage
import com.kheang.firebaseauthenticationloginregister.page.SignupPage
import com.kheang.firebaseauthenticationloginregister.screen.home.HomeScreen
import com.kheang.firebaseauthenticationloginregister.ui.screen.cart.CartScreen
import com.kheang.firebaseauthenticationloginregister.ui.screen.detail.DetailProductScreen
import com.kheang.firebaseauthenticationloginregister.ui.screen.page.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MyAppNav(modifier: Modifier = Modifier){

    val navController = rememberNavController()

    val startDestination = if (FirebaseAuth.getInstance().currentUser != null)"home" else "login"
    NavHost(navController = navController, startDestination = startDestination,builder ={

        composable ("login"){
            LoginPage(navController,AuthViewModel())
        }
        composable("signup"){
            SignupPage(modifier,navController, AuthViewModel())
        }

        composable("home"){
            HomeScreen(navController,AuthViewModel())
        }

        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            DetailProductScreen(productId, navController)
        }
        composable ("cart"){
            CartScreen(navController)
        }






    })
}