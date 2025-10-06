package com.kheang.firebaseauthenticationloginregister.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.kheang.firebaseauthenticationloginregister.viewmodel.AuthState
import com.kheang.firebaseauthenticationloginregister.viewmodel.AuthViewModel

@Composable
fun HomePage(
    navController: NavController,
    viewModel: AuthViewModel
){
    val authState = viewModel.authState.observeAsState()


    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Home Page")
        TextButton(
            onClick = {
                viewModel.logout()
            }
        ) {
            Text(text = "Logout")
        }


    }

}