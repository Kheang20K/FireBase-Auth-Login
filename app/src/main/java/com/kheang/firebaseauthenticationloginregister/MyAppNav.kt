package com.kheang.firebaseauthenticationloginregister

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kheang.firebaseauthenticationloginregister.page.HomePage
import com.kheang.firebaseauthenticationloginregister.page.LoginPage
import com.kheang.firebaseauthenticationloginregister.page.SignupPage
import com.kheang.firebaseauthenticationloginregister.viewmodel.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun MyAppNav(modifier: Modifier = Modifier){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login",builder ={

        composable ("login"){
            LoginPage(navController,AuthViewModel())
        }
        composable("signup"){
            SignupPage(modifier,navController, AuthViewModel())
        }

        composable("home"){
            HomePage(navController,AuthViewModel())
        }


    })
}