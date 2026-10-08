package com.kheang.firebaseauthenticationloginregister.ui.screen.page

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kheang.firebaseauthenticationloginregister.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenRepo : AuthRepository
) : ViewModel(){

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    fun login(
        username : String,
        password: String
    ){
        if (username.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Username and password cannot be empty")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                tokenRepo.login(
                    username = username,
                    password = password
                )
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Login error: ${e.message}", e)
                val errorMessage = when (e) {
                    is retrofit2.HttpException -> {
                        val errorJson = e.response()?.errorBody()?.string()
                        if (!errorJson.isNullOrBlank()) {
                            try {
                                org.json.JSONObject(errorJson).optString("message", e.message())
                            } catch (_: Exception) {
                                e.message()
                            }
                        } else {
                            e.message()
                        }
                    }
                    else -> e.message ?: "Login failed"
                }
                _authState.value = AuthState.Error(errorMessage)
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            tokenRepo.logout()
            _authState.value = AuthState.Unauthenticated
        }
    }

//    fun login(email: String, password: String){
//        if (email.isEmpty() || password.isEmpty()){
//            _authState.value = AuthState.Error("Email and password cannot be empty")
//            return
//        }
//
//        _authState.value = AuthState.Loading
//        auth.signInWithEmailAndPassword(email,password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful){
//                    _authState.value = AuthState.Authenticated
//                }else{
//                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
//                }
//            }
//    }

    fun signup(email: String, password: String){
        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }
//    fun logout(){
//        auth.signOut()
//        _authState.value = AuthState.Unauthenticated
//    }

}

sealed class AuthState{
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading : AuthState()

    data class Error(val message: String): AuthState()
}