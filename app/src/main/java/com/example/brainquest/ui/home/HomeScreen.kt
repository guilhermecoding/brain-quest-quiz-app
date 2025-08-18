package com.example.brainquest.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    HomeScreenContent()
}

@Composable
fun HomeScreenContent() {
    Scaffold { paddinValues ->
        Column(
            modifier = Modifier.padding(paddinValues)
        ) {
            Text("Bem-vindo ao BrainQuest!")
        }
    }
}