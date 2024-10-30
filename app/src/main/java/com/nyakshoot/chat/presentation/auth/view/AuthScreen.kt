package com.nyakshoot.chat.presentation.auth.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nyakshoot.chat.presentation.auth.viewmodel.AuthState
import com.nyakshoot.chat.presentation.auth.viewmodel.AuthViewModel
import com.nyakshoot.chat.presentation.chats.view.ChatsScreen

class AuthScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: AuthViewModel = getViewModel()
        val handleState = viewModel.authState.observeAsState()

        Log.d("test", handleState.value.toString())


        val context = LocalContext.current
        var isLoading by rememberSaveable { mutableStateOf(false) }
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.resetAuthState()
                },
                label = { Text("Email") }, // todo res
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    if (handleState.value == AuthState.InputError.Email) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Email error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (handleState.value == AuthState.InputError.Email)
                        Icon(
                            Icons.Outlined.Warning,
                            "error",
                            tint = MaterialTheme.colorScheme.error
                        )
                }
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    viewModel.resetAuthState()
                },
                label = { Text("Password") }, // todo res
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                supportingText = {
                    if (handleState.value == AuthState.InputError.Password) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Password error",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (handleState.value == AuthState.InputError.Password)
                        Icon(
                            Icons.Outlined.Warning,
                            "error",
                            tint = MaterialTheme.colorScheme.error
                        )
                }
            )
            Button(
                onClick = {
                    viewModel.logIn(email, password)
                },
                modifier = Modifier.width(250.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Yellow
                    )
                } else {
                    Text("Log in") // todo res
                }
            }
        }

        LaunchedEffect(handleState.value) {
            when (handleState.value) {
                is AuthState.AuthError -> {
                    viewModel.resetAuthState()
                    isLoading = false
                    Toast.makeText(
                        context,
                        (handleState.value as AuthState.AuthError).logInError,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                AuthState.Authorized -> {
                    isLoading = false
                    navigator.replace(ChatsScreen())
                }

                AuthState.Authorizing -> {
                    isLoading = true
                }

                AuthState.Empty,
                AuthState.InputError.Email,
                AuthState.InputError.Password,
                null -> Unit
            }
        }
    }
}