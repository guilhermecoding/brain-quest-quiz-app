package com.example.brainquest.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(), // O Hilt fornece o ViewModel
    onLogout: () -> Unit // Callback para avisar a MainActivity para navegar
) {
    HomeScreenContent(
        modifier = modifier,
        onLogoutClicked = {
            viewModel.onLogoutClicked() // Chama a lógica de logout no ViewModel
            onLogout() // Dispara o evento de navegação
        }
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    onLogoutClicked: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bem-vindo ao BrainQuest!")
            // Aqui você adicionaria a lista de quizzes, o botão de ranking, etc.

            Button(onClick = onLogoutClicked) {
                Text("Sair")
            }
        }
    }
}