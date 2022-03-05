package com.example.finalapp_nativeprog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserViewModel: ViewModel() {
    val userName = mutableStateOf<String>("Jakub")


    fun loginUser(
        email: String,
        password: String
    ) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                userName.value = email
            }
    }

    fun logoutUser(){
        Firebase.auth.signOut()

        userName.value = ""
    }
}