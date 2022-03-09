package com.example.finalapp_nativeprog

import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.firebase.components.Component

const val HOME_ROUTE = "home"
const val NOTE_ROUTE = "note"

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

    // Remember state nav controller
    val navController = rememberNavController()

    Scaffold(
        topBar = {TopBarView()},
        bottomBar = {BottomBarView(navController)},
        content = { MainContentView(navController = navController)},
    )
}

@Composable
fun MainContentView(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE)
    {
        composable(route = HOME_ROUTE) {
            HomeView()
        }
        composable(route = NOTE_ROUTE) {
            NoteView()
        }
    }
}

@Composable
fun HomeView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF7AA23)),
    ) {
        Text(text = "My imagination left me, so I did what you did")
        MemeView()
    }
}

@Composable
fun MemeView() {

    val vm = viewModel<YesNoViewModel>()

    Card(
        modifier = Modifier
            .size(200.dp, 200.dp)
            .padding(20.dp),
        elevation = 5.dp
    ) {
        AsyncImage(
            model = vm.imageURL.value,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }
    Button(onClick = { vm.getNewImage() }) {
        Text(text = "Get memes")
    }
}

@Composable
fun NoteView() {

    var note by remember { mutableStateOf("") }
    val noteVM = viewModel<NoteViewModel>(LocalContext.current as ComponentActivity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7AA23))
            .padding(13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = note,
            onValueChange = {note = it},
            label = { Text(text = "Todo note")}
        )
        OutlinedButton(
            onClick = { noteVM.addNote(Note(note)) }
        ) {
            Text(text = "Add todo note")
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        noteVM.notes.value.forEach{
            Divider(thickness = 2.dp)
            Text(text = it.message)
        }
    }
}

@Composable
fun BottomBarView(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7CA43)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "home",
            modifier = Modifier.clickable {
                navController.navigate(HOME_ROUTE)
            }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_notes_24),
            contentDescription = "note",
            modifier = Modifier.clickable {
                navController.navigate(NOTE_ROUTE)
            }
        )}
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