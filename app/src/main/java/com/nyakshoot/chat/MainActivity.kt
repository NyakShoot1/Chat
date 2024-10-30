package com.nyakshoot.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.nyakshoot.chat.presentation.auth.view.AuthScreen
import com.nyakshoot.chat.ui.theme.ChatTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatTheme {
                Navigator(AuthScreen()) { navigation ->
                    val currentScreen = navigation.lastItem


                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Crossfade(
                            targetState = currentScreen,
                            modifier = Modifier.padding(innerPadding),
                            label = currentScreen.key,
                            animationSpec = tween(500)
                        ) { screen ->
                            screen.Content()
                        }
                    }
                }
            }
        }
    }
}

