package com.example.learncomposeandroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learncomposeandroom.ui.theme.LearnComposeAndRoomTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnComposeAndRoomTheme {
                UserForm()
            }
        }
    }
}

@Composable
fun UserForm(userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepository(UserDatabase.getDatabase(
    LocalContext.current).userDao())))) {
    val scope = rememberCoroutineScope()

    val users by remember { mutableStateOf(listOf<User>()) }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var ageError by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).background(color = Color.Black),
        verticalArrangement = Arrangement.Center
    ) {

        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text("List User registration", style = MaterialTheme.typography.h6, color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it
            nameError = it.isBlank() },
            label = { Text("Name") },
            isError = nameError,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )
        if (nameError) {
            Text("Name cannot be empty" , color = Color.Red, style = MaterialTheme.typography.body2)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it
                ageError = it.isBlank() },
            label = { Text("Age") },
            isError = ageError,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White
            )
        )
        if (nameError) {
            Text("Age cannot be empty" , color = Color.Red, style = MaterialTheme.typography.body2)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                nameError = name.isBlank()
                ageError = age.isBlank() || age.toIntOrNull() == null

                if (!nameError && !ageError) {
                    val user = User(name = name, age = age.toInt())
                    scope.launch {
                        userViewModel.insertUser(user)
                        successMessage = "User added successfully!"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit", style = MaterialTheme.typography.button)
        }

        Spacer(modifier = Modifier.height(16.dp))

        successMessage?.let {
            Text(it, color = Color.Green, style = MaterialTheme.typography.body1)
        }

    }




}

@Preview(showBackground = true)
@Composable
fun UserFormPreview() {
    LearnComposeAndRoomTheme {
        UserForm()
    }
}