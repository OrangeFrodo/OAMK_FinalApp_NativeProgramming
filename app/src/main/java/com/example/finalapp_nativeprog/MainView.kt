package com.example.finalapp_nativeprog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainView() {
    val userVM = viewModel<UserViewModel>()

    if(userVM.userName.value.isEmpty()){
        LoginView(userVM)
    } else {
        MainScaffoldView()
    }
}

@Composable
fun MainScaffoldView() {
    Scaffold(
        topBar = {TopBarView()},
        bottomBar = {},
        content = {},
    )
}

@Composable
fun TopBarView() {
    var userVM = viewModel<UserViewModel>()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7CA43))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = userVM.userName.value)
        OutlinedButton(onClick = { userVM.logoutUser() }) {
            Text(text = "Log out")
        }
    }
}

@Composable
fun LoginView(userVM: UserViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")}
        )
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password")},
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedButton(onClick = { userVM.loginUser(email, password) }) {
            Text(text = "Login")
        }
    }
}