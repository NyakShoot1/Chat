package com.nyakshoot.chat.presentation.chats.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.asLiveData
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.nyakshoot.chat.presentation.auth.view.AuthScreen
import com.nyakshoot.chat.presentation.chats.viewmodel.ChatsViewModel
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext

class ChatsScreen : Screen {
    @Composable
    override fun Content() {

        val viewModel: ChatsViewModel = getViewModel()
        val isDone = viewModel.isDone.observeAsState()
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Chats")
            Button(
                onClick = {
                    viewModel.logOut()
                }
            ) {
                Text("logout")
            }
        }

        LaunchedEffect(isDone.value) {
            when(isDone.value){
                true -> { navigator.replaceAll(AuthScreen()) }
                false -> {}
                null -> Unit
            }
        }
    }
}